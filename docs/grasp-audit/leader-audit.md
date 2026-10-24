# GRASP Audit — Leader

**Audited commit of `develop`:** `d1d7cccc5d0b53bd3ad395cd6368b0c75ae4a056`  
**Auditor:** Leader
**Phase:** 1 — Audit

The verdict criteria and the evidence rules are the ones agreed by the team in `docs/grasp-analysis.md`. In this repository the console class is `UI` (package `ui`), so `UI` is used everywhere in this report. Responsibilities follow the assignment table, and `TEAM.md` matches it.

## 1. Scope

| Module (requirement) | Classes and methods audited |
|---|---|
| Sale | `calculateTotal`, `addProduct`, `saveAll`, `printSale`, `canBeReturned`.  |
| SaleService | `registerSale`, `getAllSales`, `findById`, `buildSaleFromLine`, `buildSale`, `generateSaleId`.  |
| UI | `Menus`: `launch`, `personMenu`, `saleMenu`, `accessoryMenu`, `promotionMenu`, `returnMenu`, `warrantyMenu`. <br>`product module:` `registerVideoGame`, `registerConsole`, `listAllProducts`. <br>`Person module:` `registerCustomer`, `listAllCustomers`, `listAllSellers` <br>`Sale module:` `registerSale`, `listAllSales`, `salesByClient`, `salesBySeller`.<br>`Accessory module:` `registerController`, `registerCable`, `registerMemory`, `listAllAccessories`, `listAccessoriesByType`, `listAccessoriesCompatibleWithConsole`. <br>`Promotion module:` `registerPercentageDiscountUI`, `registerCategoryDiscountUI`, `registerBulkPurchaseDiscountUI`, `listAllPromotionsUI`, `listActivePromotionsUI` . <br>`Return module:` `registerReturn`, `listAllReturns`, `listReturnsByCustomer`, `listReturnsBySale`, `consultMonthlyBalance` . <br>`Warranty module:` `findWarrantyByProductAndSale`, `listAllWarranties`, `listActiveWarranties`, `listWarrantiesExpiringSoon` . | 
| Sale Repository  | `save`, `saveAll`, `findAllLines`, `toLine`, `ensureFileExists`. |
| Main | `main`. |
| Product Service | `restoreStock`. The rest of those classes belongs to Developer 1 or developer 2 and is not audited here. |

## 2. Verdict summary

| Pattern | Verdict | Violation IDs |
|---|---|---|
| Information Expert | Violation | V-18, V-19, V-20 |
| Creator | Correct | — |
| Controller | Correct | — |
| Low Coupling | Violation | V-21, V-22, V-23 |
| High Cohesion | Violation | V-24, V-25 |
| Polymorphism | Violation | V-26, V-27 |
| Pure Fabrication | Correct | — |
| Indirection | Correct | — |
| Protected Variations | Correct | — |

When a finding breaks more than one pattern, it is registered once under its guiding pattern and referenced as "related" under the others.

## 3. Pattern sections

### 3.1 Information Expert
**Verdict:** Violation V-18, V-19, V-20.

*Correct points.* `Person`, `Customer`, `Seller`, and `Product` own and expose their own data. `Sale.calculateTotal()` computes its own sum by asking each product for its price, and `Sale.canBeReturned` determines its own return eligibility based on its date without external calculation.

*Violations.* `SaleService` parses raw delimited text lines and navigates type branches via `instanceof Accessory` to decide how to update stock instead of delegating persistence and type-specific handling to the expert components (V-18). `ProductService.restoreStock` extracts and calculates stock externally using `getStockQuantity()` and `setStockQuantity()` instead of telling `Product` to update its own inventory (V-19). `SaleService` re-implements seller and customer sale filtering by directly parsing repository storage fields rather than delegating queries to the data expert (V-20).

### 3.2 Creator
**Verdict:** Correct.

Classes reviewed: `Sale`, `SaleService`, `SaleRepository`, and `Main`. The application correctly assigns creation responsibilities according to the GRASP Creator pattern. `SaleService.registerSale` acts as the legitimate creator for `Sale` instances because it aggregates, records, and initializes the required domain components (`Customer`, `Seller`, and `Product`) before persisting the resulting sale via `SaleRepository.save`. Additionally, `Main` appropriately acts as the composition root, taking charge of instantiating and wiring top-level repositories and services.

### 3.3 Controller
**Verdict:** Correct.

Classes reviewed: `UI`, `SaleService`, and supporting service classes. The application correctly separates system event handling from business logic. `UI` acts as the dedicated user interface controller, capturing console inputs via `Scanner` and managing menu flows without embedding business rules. It correctly delegates operational responsibilities to service classes such as `ProductService` (e.g., registering video games and consoles) and `SaleService` (e.g., executing sales), which handle domain processing and coordinate data validation.

### 3.4 Low Coupling
**Verdict:** Violation V-21, V-22, V-23.

*Correct points.* Domain classes (`Sale`, `Product`, `Customer`) maintain independent data representation without importing UI or persistence layer components, allowing domain logic to remain isolated from presentation details.

*Violations.* `UI` is directly coupled to 7 concrete service implementations and instantiates domain models directly inside console prompts instead of depending on service abstractions (V-21). `SaleService` utilizes static state for repository and service references, creating rigid global coupling and preventing dependency injection during testing (V-22). `SaleService` exhibits high cross-service coupling by directly querying `AccessoryService` and performing type checks (`instanceof Accessory`) to divert updates, forcing the sale module to change whenever accessory persistence behavior changes (V-23).

### 3.5 High Cohesion
**Verdict:** Violation V-24, V-25.

*Correct points.* Domain model classes such as `Customer`, `Seller`, `Product`, and `Sale` maintain high cohesion, focusing exclusively on representing core business attributes and directly related domain behavior.

*Violations.* The `UI` class exhibits low cohesion by combining multiple distinct functional modules into a single class—handling input, text formatting, and menu navigation across products, persons, sales, accessories, promotions, returns, and warranties simultaneously (V-24). Similarly, `SaleService` violates cohesion by mixing core business orchestration for sales registration with low-level data parsing (`split(";")`), raw text-line filtering, and storage record reconstruction logic that should belong to the persistence layer (V-25).

### 3.6 Polymorphism
**Verdict:** Violation V-26, V-27.

*Correct points.* Dynamic dispatch is correctly applied when evaluating promotions via `promotion.calculateDiscount(sale)`, allowing distinct promotion subclasses to calculate their discounts polymorphically without requiring explicit type checks.

*Violations.* `SaleService.registerSale` relies on explicit type conditionals (`instanceof Console`) to determine whether to assign a basic warranty, which requires modifying the service every time a new product type with basic warranty rules is introduced (V-26). Similarly, `SaleService` uses type checks (`instanceof Accessory`) to split persistence and update logic between `AccessoryService` and `ProductService`, violating polymorphism by selecting behavior through type operators instead of delegating via a unified update method in the product abstraction (V-27).

### 3.7 Pure Fabrication
**Verdict:** Correct.

*Correct points.* The architecture successfully introduces pure fabrication classes to handle technical and cross-cutting concerns separate from core domain logic. Specifically, validation responsibilities are cleanly delegated to dedicated validator classes (`PersonValidator`, `ProductValidator`, and `SaleValidator`) utilized by `SaleService`, rather than embedding inline validation checks inside domain entities. Similarly, persistence mechanisms such as `SaleRepository` abstract away low-level file reading and writing operations from the business services.

### 3.8 Indirection
**Verdict:** Correct.

*Correct points.* The architecture successfully introduces intermediate components to decouple components and prevent direct, rigid dependencies between the presentation layer and data storage. Specifically, service classes (`ProductService`, `PersonService`, `SaleService`) and persistence repositories (`SaleRepository`) act as effective intermediaries, decoupling the user interface (`UI`) from low-level data access and ensuring that changes in storage mechanisms or data formats do not ripple directly into the presentation layer.

### 3.9 Protected Variations
**Verdict:** Correct.

*Correct points.* The architecture successfully isolates points of foreseeable instability behind dedicated abstractions and helper classes. Specifically, validation logic is encapsulated within specialized validator classes (`PersonValidator`, `ProductValidator`, and `SaleValidator`), protecting core services from modifications when validation rules change. Furthermore, promotion evaluations are isolated through polymorphism, ensuring that introducing new types of promotions or discount rules does not force extensive modifications across the sales processing logic.

## 4. Violations

### V-18

| Field | Content |
|---|---|
| ID | V-18 |
| Pattern | Information Expert (guiding pattern); also Low Coupling |
| Module | Sales and Inventory (`SaleService`) |
| Evidence | `SaleService.registerSale`: parses raw delimited text lines and navigates type branches via `instanceof Accessory` to decide how to update stock instead of delegating persistence and type-specific handling to the expert components. |
| Explanation | `SaleService` assumes responsibilities for managing stock updates and handling specific types directly rather than delegating these actions to the corresponding expert domain components (`Product` or `Accessory`). |
| Consequence | Creates rigid coupling between the sales service and the internal persistence structure of accessories and products, forcing modifications to `SaleService` whenever inventory management details change. |
| Proposed solution | Delegate stock updating and specific persistence routing directly to the expert classes (`Product` and `Accessory`) through polymorphic methods. |
| Code from another team member | Yes: interacts with product and accessory model classes (Developer 1). |

### V-19

| Field | Content |
|---|---|
| ID | V-19 |
| Pattern | Information Expert (guiding pattern) |
| Module | Products (`ProductService`) |
| Evidence | `ProductService.restoreStock`: extracts and calculates stock externally using `getStockQuantity()` and `setStockQuantity()` instead of telling `Product` to update its own inventory. |
| Explanation | The external service performs calculations and direct modifications on product stock attributes, violating the "Tell, Don't Ask" principle where the data expert should manage its own state. |
| Consequence | If the internal logic for adjusting or validating stock changes (e.g., minimum stock thresholds or rules), the service must be modified rather than encapsulating the rule within the `Product` entity. |
| Proposed solution | Move stock restoration and adjustment logic directly to a method inside the `Product` class (e.g., `restoreStock(int amount)`). |
| Code from another team member | Yes: the `Product` class belongs to Developer 1. |

### V-20

| Field | Content |
|---|---|
| ID | V-20 |
| Pattern | Information Expert (guiding pattern); also Low Coupling |
| Module | Sales (`SaleService`) |
| Evidence | `SaleService.getSalesByCustomer` and `getSalesBySeller`: re-implement seller and customer sale filtering by directly parsing repository storage fields (`line.split(";")`) rather than delegating queries to the data expert. |
| Explanation | The sales service handles low-level parsing and filtering tasks that belong to the repository or specialized data query mechanisms, violating storage data encapsulation. |
| Consequence | Any change to the file formatting of sales records breaks multiple query methods inside the service. |
| Proposed solution | Delegate querying and filtering by customer or seller directly to `SaleRepository`, allowing the repository to return filtered domain collections without exposing storage formatting details. |
| Code from another team member | No |

### V-21

| Field | Content |
|---|---|
| ID | V-21 |
| Pattern | Low Coupling (guiding pattern); also High Cohesion |
| Module | User Interface (`UI`) |
| Evidence | `UI`: is directly coupled to 7 concrete service implementations (`SaleService`, `PersonService`, `ProductService`, `AccessoryService`, `PromotionService`, `ReturnService`, `WarrantyService`) and instantiates domain models directly inside console prompts. |
| Explanation | The presentation class manages an excessive number of concrete service dependencies and participates directly in domain entity instantiation, mixing presentation responsibilities with control logic and concrete service knowledge. |
| Consequence | The user interface is highly fragile; any modification to service constructors or signatures forces changes across the `UI` class. |
| Proposed solution | Reduce coupling by introducing service interfaces or abstractions and isolating entity instantiation logic away from console prompts. |
| Code from another team member | Yes: involves services developed by other team members. |

### V-22

| Field | Content |
|---|---|
| ID | V-22 |
| Pattern | Low Coupling (guiding pattern); also Indirection |
| Module | Sales (`SaleService`) |
| Evidence | `SaleService`: utilizes static state for repository and service references (`private static SaleRepository saleRepository`, etc.) while simultaneously defining an instance constructor. |
| Explanation | Combining static global fields with instance initialization creates hidden global state, preventing clean dependency injection and obstructing unit testing. |
| Consequence | Concurrent execution or automated testing can silently overwrite global repository and service references, leading to unpredictable side effects. |
| Proposed solution | Convert static fields into `final` instance fields and remove static methods from `SaleService` to enable clean dependency injection. |
| Code from another team member | Yes: requires coordination with consuming classes in the persistence layer and `Main` (Technical Lead). |

### V-23

| Field | Content |
|---|---|
| ID | V-23 |
| Pattern | Low Coupling (guiding pattern); also Polymorphism |
| Module | Sales (`SaleService`) |
| Evidence | `SaleService.registerSale`: performs direct cross-service queries to `AccessoryService` and uses type checks (`instanceof Accessory`) to divert stock updates to the accessory service instead of treating items generically as products. |
| Explanation | The sales module possesses explicit knowledge about storage separation between accessories and general products, causing high cross-service coupling. |
| Consequence | Modifying how accessories are updated or persisted requires editing sales registration logic. |
| Proposed solution | Unify the update mechanism through a common product interface, allowing `SaleService` to interact polymorphically without type checking. |
| Code from another team member | Yes: interacts with `AccessoryService` (Developer 2). |

### V-24

| Field | Content |
|---|---|
| ID | V-24 |
| Pattern | High Cohesion (guiding pattern) |
| Module | User Interface (`UI`) |
| Evidence | `UI`: combines multiple distinct functional modules into a single class—handling input, text formatting, and menu navigation across products, persons, sales, accessories, promotions, returns, and warranties simultaneously. |
| Explanation | The class lacks functional cohesion by concentrating console interaction for the entire application into a single massive class with unrelated responsibilities. |
| Consequence | High maintenance complexity; adding or modifying a single menu option risks breaking unrelated application sections. |
| Proposed solution | Split `UI` into specialized module controllers (e.g., `ProductMenuUI`, `SaleMenuUI`) or delegate display responsibilities cleanly. |
| Code from another team member | No (Technical Lead / UI). |

### V-25

| Field | Content |
|---|---|
| ID | V-25 |
| Pattern | High Cohesion (guiding pattern); also Information Expert |
| Module | Sales (`SaleService`) |
| Evidence | `SaleService`: mixes core business orchestration for sales registration with low-level data parsing (`split(";")`), raw text-line filtering, and storage record reconstruction logic. |
| Explanation | The service mixes persistence layer concerns (plain text record parsing) with core business orchestration logic. |
| Consequence | Violates architectural layer separation, causing file layout changes to directly impact business service code. |
| Proposed solution | Move line parsing and record reconstruction logic into `SaleRepository`, keeping the service completely decoupled from storage details. |
| Code from another team member | No |

### V-26

| Field | Content |
|---|---|
| ID | V-26 |
| Pattern | Polymorphism (guiding pattern); also Protected Variations |
| Module | Sales and Warranties (`SaleService`) |
| Evidence | `SaleService.registerSale`: relies on explicit type conditionals (`if (product instanceof Console)`) to determine whether to assign a basic warranty. |
| Explanation | Subtype-varying behavior is resolved through explicit type checks rather than dynamic dispatch or polymorphic methods on the product hierarchy. |
| Consequence | Adding a new product type requiring an automatic basic warranty forces modifications to `SaleService`. |
| Proposed solution | Add a polymorphic method to `Product` (e.g., `hasBasicWarranty()`, returning `false` by default and overridden in `Console`) for the service to query. |
| Code from another team member | Yes: requires modifying the `Product` hierarchy (Developer 1). |

### V-27

| Field | Content |
|---|---|
| ID | V-27 |
| Pattern | Polymorphism (guiding pattern); also Low Coupling |
| Module | Sales (`SaleService`) |
| Evidence | `SaleService.registerSale`: uses type checks (`if (product instanceof Accessory)`) to split persistence and update logic between `AccessoryService` and `ProductService`. |
| Explanation | Update service selection is made via explicit type operators rather than delegating through a unified update abstraction. |
| Consequence | Introducing any new inventory item category forces edits to type branching conditionals in `SaleService`. |
| Proposed solution | Provide a unified update method or entry point in the persistence layer so the sales orchestrator does not need to distinguish storage mechanisms between products and accessories. |
| Code from another team member | Yes: involves `AccessoryService` (Developer 2). |

## 5. Issues involving Technical Lead's components & cross-module coordination

| Class / method | Owner | Description and evidence | Related Violations / Findings |
|---|---|---|---|
| `SaleService.registerSale` and accessory handling | Technical Lead | `Accessory` is handled through a parallel service and repository structure, forcing `SaleService` to branch via type checks (`instanceof Accessory`) and duplicate lookup/update paths (`accessoryService.update` vs. `productService.update`). | V-18, V-23, V-27 (B-4) |
| Static API and shared state in `SaleService` | Technical Lead | `SaleService` maintains collaborators in static fields while exposing static methods (`getAllSales`, `findById`), creating hidden global state and preventing clean dependency injection. | V-22 (B-2) |
| Persistence layer service dependencies | Technical Lead / Developer 2 | Repositories managed across modules (`ReturnRepository`, `WarrantyRepository`) depend directly on service classes, violating the layer rule (`ui → service → persistence → model`) and requiring setter workarounds in `Main`. | V-21, V-22 (B-1) |
| `UI` entity instantiation & accessory menus | Technical Lead / Developer 2 | `UI` directly instantiates domain entities (`new Controller`, `new Cable`, `new Memory`) and handles hard-coded type strings in menu flows instead of delegating creation and identification to service/domain abstractions. | V-21 (B-5, B-6) |
| Verification of open VERIFY items & functional notes | Technical Lead | **D-3:** Extended warranty costs are not added to sale totals in `Sale.calculateTotal()`.<br>**D-5:** Accessory registration order prevents saving compatible consoles unless updated explicitly.<br>**D-8:** Warranties persist immediately before sale transaction finalization in `SaleService`. | Out-of-scope / VERIFY items |

## 6. Possible functional defects and items outside scope

These items were identified during code review. Because correcting them alters observable program behavior, console outputs, or data file structures, they are classified as out-of-scope for this phase and must be preserved during refactoring.

| Class / method | Evidence | Observed behavior / reason |
|---|---|---|
| Warranties of the same sale (`WarrantyRepository.loadAll`, `WarrantyService.assign...`, `SaleService.registerSale`) | `WarrantyRepository.loadAll`: `Sale sale = findSaleById(data[3]); ... if (product == null \|\| sale == null) continue;`. `WarrantyService.assignBasicWarranty` / `assignExtendedWarranty`: `repository.loadAll()`, `add`, `repository.saveAll(...)`. `SaleService.registerSale` creates warranties before `saleRepository.save(sale)` (Related to D-8). | The sale is not yet stored when the second warranty of the same sale is created, so the first warranty is skipped during loading and overwritten by `saveAll`. A sale with multiple warranties loses all but the last one. |
| `ReturnService.generateMonthlyBalance` | `totalSales += sale.calculateTotal();` and `totalReturns += returnItem.getRefundAmount();` | `calculateTotal()` sums product prices only. Consequently, the net balance fails to account for promotion discounts, unrecorded reloaded discounts (D-2), or extra warranty costs (D-3). Correcting this calculation changes reported figures. |
| Repository serialization (`PersonRepository`, `AccessoryRepository`, `PromotionRepository`, `ReturnRepository`) | Fields joined with `","` and read via `line.split(",")` where free-text fields include `name`, `title`, and return `reason`. | A comma embedded in any free-text field shifts field indexing during line parsing or truncates return reasons. |
| `ProductService.registerConsole` | `registerConsole` instantiates `Console` directly without validation, whereas `registerVideoGame` invokes `ProductValidator.validateProductData`. | Consoles with invalid data are permitted while video games are strictly filtered, creating an inconsistency between product registration pathways. |
| Error handling and console output discrepancies | Repositories throw generic `RuntimeException` or write to `System.err` via `ensureFileExists`; `ReturnService` uses English `IllegalArgumentException` messages. | These components missed prior exception refactoring, bypassing the custom exception hierarchy and emitting raw console outputs that conflict with localization and verification preservation rules. |
