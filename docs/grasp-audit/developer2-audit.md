# GRASP Audit — Developer 2

**Audited commit of `develop`:** `d1d7cccc5d0b53bd3ad395cd6368b0c75ae4a056`  
**Auditor:** Developer 2  
**Phase:** 1 — Audit

The verdict criteria and the evidence rules are the ones agreed by the team in `docs/grasp-analysis.md`. In this repository the console class is `UI` (package `ui`), so `UI` is used everywhere in this report. Responsibilities follow the assignment table, and `TEAM.md` matches it.

## 1. Scope

| Module (requirement) | Classes and methods audited |
|---|---|
| Persons (Workshop 1) | `Person`, `Customer` (including `getPurchaseHistory`, `addPurchase`), `Seller`.<br>`PersonRepository`: `saveAll`, `loadAll`, `findCustomerById`, `findSellerById`, `ensureFileExists`, `initializeDefaultSellers`.<br>`PersonService`: `registerCustomer`, `getAllCustomers`, `getAllSellers`, `findCustomerById`, `findSellerById`. |
| Accessories (Req. 1) | `AccessoryRepository`: `saveAll`, `loadAll`, `findById`, `joinConsoleIds`, `splitConsoleIds`, `ensureFileExists`, `initializeDefaultAccessories`.<br>`AccessoryService`: `registerController`, `registerCable`, `registerMemory`, `getAllAccessories`, `getAccessoriesByType`, `getCompatibleAccessories`, `findById`, `hasStock`, `update`. |
| Promotions (Req. 2) | `PromotionRepository`: `saveAll`, `loadAll`, `ensureFileExists`.<br>`PromotionService`: `registerPercentageDiscount`, `registerCategoryDiscount`, `registerBulkPurchaseDiscount`, `listAllPromotions`, `listActivePromotions`, `findBestPromotionFor`, `findById`. |
| Returns (Req. 3) | `ReturnRepository`: `saveAll`, `loadAll`, `findSaleInService`, `setSaleService`, `setProductService`, `ensureFileExists`.<br>`ReturnService`: `registerReturn`, `viewAllReturns`, `viewReturnsByCustomer`, `viewReturnsBySale`, `generateMonthlyBalance`, and its private helpers. |
| Warranties (Req. 4) | `WarrantyRepository`: constructors, `saveAll`, `loadAll`, `findSaleById`, `setSaleService`, `ensureFileExists`.<br>`WarrantyService`: `assignBasicWarranty`, `assignExtendedWarranty`, `findWarrantyByProduct`, `listAllWarranties`, `listActiveWarranties`, `listWarrantiesExpiringSoon`. |
| Exceptions and validations (Req. 6) | Only the Req. 6 modifications made by Developer 2: the conversion of `IOException` into `PersistenceException` in `PersonRepository`, `ProductRepository` and `SaleRepository`; the validator calls in `PersonService`, `ProductService` (`registerConsole`, `registerVideoGame`) and `SaleService` (`registerSale`, `getSalesBySeller`, `getSalesByCustomer`). The rest of those classes belongs to Developer 1 or the Technical Lead and is not audited here. |

## 2. Verdict summary

| Pattern | Verdict | Violation IDs |
|---|---|---|
| Information Expert | Violation | V-12, V-15, V-16 (V-05 related) |
| Creator | Violation | V-07 |
| Controller | Not applicable | — |
| Low Coupling | Violation | V-03, V-10, V-11, V-14 (V-09, V-17 related) |
| High Cohesion | Violation | V-02, V-06 |
| Polymorphism | Violation | V-01, V-04, V-05, V-08, V-13 |
| Pure Fabrication | Correct | — |
| Indirection | Violation | V-17 (V-10, V-14 related) |
| Protected Variations | Violation | V-09 (V-01, V-04, V-08, V-13, V-17 related) |

When a finding breaks more than one pattern, it is registered once under its guiding pattern and referenced as "related" under the others.

## 3. Pattern sections

### 3.1 Information Expert
**Verdict:** Violation V-12, V-15, V-16, (V-05 related).

*Correct points.* `Person`, `Customer` and `Seller` own and expose their own data. `PromotionService.findBestPromotionFor` asks each promotion for its own discount (`promotion.calculateDiscount(sale)`), and `PromotionService.listActivePromotions` and `WarrantyService.listActiveWarranties` ask each promotion or warranty whether it is active (`isActive(today)`). `ReturnService.generateMonthlyBalance` does not recompute the sale total: it asks each sale for it (`sale.calculateTotal()`) and each return for its refund (`getRefundAmount()`).

*Violations.* `ReturnService.viewReturnsByCustomer` navigates a chain of getters to decide who owns a return (V-12). The lookup of a sale by id is reimplemented in two Developer 2 classes although the class that stores sales already offers it (V-15). `WarrantyService` decides on expiry and coverage using data that belongs to the warranty (V-16). `AccessoryService.getCompatibleAccessories` decides on data that belongs to the accessory (registered under V-05).

### 3.2 Creator
**Verdict:** Violation (V-07).

*Correct points.* `PersonService.registerCustomer`, `PromotionService.register...Discount`, `ReturnService.registerReturn` and `WarrantyService.assignBasicWarranty` / `assignExtendedWarranty` create the objects they record from the primitives they receive, which is the same shape as `ProductService.registerVideoGame`. `Customer` creates its own `purchaseHistory` because it contains it. The repositories (`PersonRepository`, `AccessoryRepository`, `PromotionRepository`, `ReturnRepository`, `WarrantyRepository`) create objects in `loadAll` from the file fields, since they hold the initializing data. The creation of the default sellers and accessories is legitimate as an act of creation; the concern about where that seeding lives is registered under High Cohesion (V-02, V-06).

*Violation.* `AccessoryService.registerController`, `registerCable` and `registerMemory` receive already-built entities, so the caller has to create them (V-07).

### 3.3 Controller
**Verdict:** Not applicable.

Classes reviewed: all the classes listed in Section 1. None of them receives events from the user interface, and none uses `Scanner` or `System.out`. System events are received by `UI` (Technical Lead), which delegates to the services.

`PromotionRepository`, `ReturnRepository` and `WarrantyRepository` print to `System.err` when the data file cannot be created. Removing that output would change what the program emits, so it is listed in Section 6 and not registered as a violation.

### 3.4 Low Coupling
**Verdict:** Violation V-03, V-10, V-11, V-14, (V-09, V-17 related).

*Correct points.* The services receive their repositories through the constructor and never instantiate them, and the Persons, Accessories and Promotions repositories depend only on the model.

*Violations.* The cyclic dependency between `Customer` and `Sale` (V-03); the persistence layer depending on the service layer in `ReturnRepository` and `WarrantyRepository` (V-10, V-14); and the hidden static dependencies of `ReturnService` (V-11).

### 3.5 High Cohesion
**Verdict:** Violation (V-02, V-06).

`Person`, `Seller`, `PersonService`, `AccessoryService`, `PromotionService`, `WarrantyService` and the Promotions, Returns and Warranties repositories can each be described in one sentence without "and". The violations are the seed data inside the Persons and Accessories repositories (V-02, V-06).

`ReturnService.generateMonthlyBalance` consolidates sales and returns, which is not strictly a returns operation. It is not registered as a violation because the Req. 3 specification assigns the balance to this service on purpose, as the service layer class that consolidates both modules.

### 3.6 Polymorphism
**Verdict:** Violation (V-01, V-04, V-05, V-08, V-13).

*Correct point.* `PromotionService.findBestPromotionFor` works with the abstract `Promotion` and calls `calculateDiscount` without checking the promotion subclass.

*Violations.* The type tests in the four repositories (V-01, V-04, V-08, V-13) and in `AccessoryService` (V-05).

### 3.7 Pure Fabrication
**Verdict:** Correct.

File layout, parsing and writing live in dedicated repository classes, so the model classes (`Person`, `Customer`, `Seller` and the others) contain no I/O code. `AccessoryRepository.joinConsoleIds` and `splitConsoleIds` keep the storage format of the compatible-console list inside the persistence layer. Validation lives in `PersonValidator` and `ProductValidator`, called from `PersonService` and `ProductService`, and `ProductRepository`, `PersonRepository` and `SaleRepository` convert `IOException` into `PersistenceException`.

The preparation of the data file (`ensureFileExists`) is repeated in each repository. It is not registered because it sits in the class that should own it, so it does not break the criterion.

### 3.8 Indirection
**Verdict:** Violation V-17, (V-10, V-14 related).

*Correct points.* Callers obtain people, accessories, promotions and warranties through their services instead of reading files, and the repositories sit between the services and the files.

*Violations.* The services are bound directly to the concrete file-based repository classes (V-17), and the persistence layer reaches the services directly in `ReturnRepository` and `WarrantyRepository` (V-10, V-14).

### 3.9 Protected Variations
**Verdict:** Violation V-09, (V-01, V-04, V-08, V-13, V-17 related).

The unprotected variation points are the storage mechanism, whose `java.io` exception leaks into the promotion service signatures (V-09), and the addition of new person, accessory, promotion or warranty types, whose type-dependent conditionals are registered under Polymorphism (V-01, V-04, V-08, V-13). The direct dependency on concrete repositories is registered under Indirection (V-17).

*Correct points.* `PromotionService.findBestPromotionFor` does not change when a new promotion type is added, `PersistenceException` isolates the upper layers from storage errors in `PersonRepository`, `ProductRepository` and `SaleRepository`, and the repository constructors that receive a `filePath` allow changing the file location without touching the classes.

## 4. Violations

### V-01

| Field | Content |
|---|---|
| ID | V-01 |
| Pattern | Polymorphism (guiding pattern); also Protected Variations |
| Module | Persons (Workshop 1) |
| Evidence | `PersonRepository.saveAll`: `if (person instanceof Customer) { ... } else if (person instanceof Seller) { ... }` (no `else` branch).<br>`PersonRepository.loadAll`: `if (data[0].equals("CUSTOMER")) { ... } else if (data[0].equals("SELLER")) { ... }`.<br>`PersonRepository.findCustomerById` and `findSellerById`: `if (person instanceof Customer && person.getId().equals(id))` and its `Seller` equivalent.<br>`PersonService.getAllCustomers` and `getAllSellers`: `if (person instanceof Customer)` and `if (person instanceof Seller)`. |
| Explanation | `Person` has subclasses but defines no polymorphic operation. The behavior that varies by subtype (how each one is written to the file, rebuilt from it, and selected from a list) is resolved with type tests repeated in two classes instead of being delegated to the type. |
| Consequence | Adding a new subtype (for example, `Supplier`) requires editing at least six methods in two classes. Forgetting one has no visible error: `saveAll` has no `else` branch, so a person of an unknown subtype is silently not saved. |
| Proposed solution | In the persistence package, introduce a small serializer abstraction per person type (it states whether it handles a given person, converts it to a line, and rebuilds it from the fields), with one implementation for `Customer` and one for `Seller`. `PersonRepository` iterates over the available serializers instead of branching. The typed lookups in the repository and in the service are replaced by one generic method parameterized by type. The file format stays identical. |
| Code from another team member | No |

### V-02

| Field | Content |
|---|---|
| ID | V-02 |
| Pattern | High Cohesion |
| Module | Persons (Workshop 1) |
| Evidence | `PersonRepository(String filePath)` calls `ensureFileExists(); initializeDefaultSellers();`.<br>`PersonRepository.initializeDefaultSellers` hard-codes three sellers (`new Seller("0001", "Carlos Gomez", "3105456211", "EMP001", "Morning")` and two more) and writes them with `saveAll(defaultSellers)`. |
| Explanation | The one-sentence description of this class needs an "and": it persists people and it owns business seed data, writing to the file as a side effect of its constructor. It has two reasons to change: the storage mechanism and the initial data. |
| Consequence | Changing the preloaded sellers requires editing a persistence class. Creating a repository modifies the file, which makes it harder to reuse or test in isolation. |
| Proposed solution | Move the seed data and its initialization to a dedicated initializer class (Pure Fabrication) that uses the repository through `loadAll` and `saveAll`, and call it at startup. The file content after startup must remain identical. |
| Code from another team member | Yes: `Main` (Technical Lead), the composition root, would call the initializer. |

### V-03

| Field | Content |
|---|---|
| ID | V-03 |
| Pattern | Low Coupling |
| Module | Persons (Workshop 1) |
| Evidence | `Customer`: `private List<Sale> purchaseHistory;`, `getPurchaseHistory()` and `addPurchase(Sale sale)`.<br>`Sale` holds its customer: `SaleService.registerSale` builds `new Sale(saleId, LocalDate.now(), customer, seller, products)` and `SaleRepository.toLine` uses `sale.getCustomer().getId()`. |
| Explanation | `Customer` and `Sale` depend on each other, a cycle between the Persons and Sales modules. In addition, the purchases of a customer can already be obtained from `SaleService.getSalesByCustomer`, so the history inside `Customer` duplicates that information. |
| Consequence | A change in `Sale` forces changes in `Customer`, and neither class can be reused without the other. `PersonRepository.loadAll` rebuilds every `Customer` with an empty history, so the list never reflects the stored sales. |
| Proposed solution | Remove the history from `Customer` (attribute, getter and `addPurchase`) and rely on `SaleService.getSalesByCustomer`. Any class that uses these members is adapted to that service method. |
| Code from another team member | Only if other classes use `getPurchaseHistory` or `addPurchase`; their owner adapts them. |

### V-04

| Field | Content |
|---|---|
| ID | V-04 |
| Pattern | Polymorphism (guiding pattern); also Protected Variations |
| Module | Accessories (Req. 1) |
| Evidence | `AccessoryRepository.saveAll`: `if (accessory instanceof Controller) { ... } else if (accessory instanceof Cable) { ... } else if (accessory instanceof Memory) { ... }` (no `else` branch).<br>`AccessoryRepository.loadAll`: `if (data[0].equals("CONTROLLER")) { ... } else if (data[0].equals("CABLE")) { ... } else if (data[0].equals("MEMORY")) { ... }`. |
| Explanation | How each accessory type is written to and rebuilt from the file is decided by type tests in the repository instead of being delegated to the type. Each branch also repeats the common fields (`id`, `title`, `price`, `stockQuantity`). |
| Consequence | Adding a new accessory type (for example, a headset) requires editing both methods. If `saveAll` is not updated, accessories of the new type are silently not saved because there is no `else` branch. |
| Proposed solution | Same design as V-01: one serializer per accessory type behind a common abstraction in the persistence package, with the repository iterating over them. The file format and the `CONTROLLER`/`CABLE`/`MEMORY` tags stay identical. |
| Code from another team member | No |

### V-05

| Field | Content |
|---|---|
| ID | V-05 |
| Pattern | Polymorphism (guiding pattern); also Information Expert |
| Module | Accessories (Req. 1) |
| Evidence | `AccessoryService.getAccessoriesByType`: `if (accessory instanceof Controller) { type = "CONTROLLER"; } else if (accessory instanceof Cable) { type = "CABLE"; } else if (accessory instanceof Memory) { type = "MEMORY"; }`, compared with the `String accessoryType` received.<br>`AccessoryService.getCompatibleAccessories`: `if (accessory instanceof Controller) { Controller controller = (Controller) accessory; if (controller.getCompatibleConsoles().contains(consoleId)) ... } else if (accessory instanceof Memory) { ... }`. |
| Explanation | The type tags that already exist in `AccessoryRepository` are rebuilt here with another chain of type tests, and the accessory type is identified by a string. In `getCompatibleAccessories`, `getCompatibleConsoles()` is declared in `Accessory`, so the type tests and casts are unnecessary, and the service asks each accessory for its data and decides on its own instead of asking the accessory whether it is compatible. |
| Consequence | A new accessory type requires editing these methods too, and the type tags are duplicated in two classes. A type that supports compatible consoles but is not listed in the chain is silently ignored. |
| Proposed solution | Let `Accessory` answer both questions (an identifier of its type, and whether it is compatible with a console) and make the service iterate over `Accessory` without casts or type strings. The results for the current data must remain identical, and the text printed by `UI` for the type keeps its current content. |
| Code from another team member | Yes: `Accessory` (Developer 1) provides the type identifier, and `UI` (Technical Lead) adapts its accessory-type menu. Related to boundary candidate B-6 of `grasp-analysis.md`. |

### V-06

| Field | Content |
|---|---|
| ID | V-06 |
| Pattern | High Cohesion |
| Module | Accessories (Req. 1) |
| Evidence | `AccessoryRepository(String filePath)` calls `ensureFileExists(); initializeDefaultAccessories();`.<br>`AccessoryRepository.initializeDefaultAccessories` hard-codes three accessories (for example `new Controller("AC001", "Wireless remote Pro", 180000.0, 15, "WIRELESS")`) and the console reference `"CON001"`, then writes them with `saveAll(defaultAccessories)`. |
| Explanation | Same as V-02: the class that persists accessories also owns business seed data, writes to the file from its constructor, and embeds a reference to a console id that belongs to the product data. |
| Consequence | Changing the preloaded accessories requires editing a persistence class, and the seed data silently depends on a console `CON001` existing in another file. |
| Proposed solution | Same approach as V-02: move the seed data to a dedicated initializer (Pure Fabrication), keeping the file content after startup identical. |
| Code from another team member | Yes: `Main` (Technical Lead), the composition root, would call the initializer. |

### V-07

| Field | Content |
|---|---|
| ID | V-07 |
| Pattern | Creator (guiding pattern); also Controller |
| Module | Accessories (Req. 1) |
| Evidence | `AccessoryService.registerController(Controller controller)`, `registerCable(Cable cable)` and `registerMemory(Memory memory)`, each only calling `registerAccessory(...)` with the entity received.<br>For contrast, `ProductService.registerVideoGame(String id, String title, ...)` and `PersonService.registerCustomer(String id, String name, ...)` receive primitives and create the entity. |
| Explanation | The service records the accessories (and holds what is needed to build them), but the caller creates them. In this repository the caller is `UI`, which builds `Controller`, `Cable` and `Memory` entities, as the Technical Lead reports in boundary candidate B-5 of `grasp-analysis.md`. The Developer 2 side of that finding is the signature of these three methods. |
| Consequence | A new accessory attribute or type edits `UI` and its constructor calls, and the creation rules are split between layers. |
| Proposed solution | `AccessoryService.registerController`, `registerCable` and `registerMemory` receive primitives and create the entity, as `ProductService` does. The persisted result must stay identical, including the order in which the compatible consoles are set, which the Technical Lead describes in B-5. |
| Code from another team member | Yes: `UI` (Technical Lead) removes its `new` calls after the service signatures change. Developer 2 changes the service first. |

### V-08

| Field | Content |
|---|---|
| ID | V-08 |
| Pattern | Polymorphism (guiding pattern); also Protected Variations |
| Module | Promotions (Req. 2) |
| Evidence | `PromotionRepository.saveAll`: `if (promotion instanceof PercentagePromotion) { ... } else if (promotion instanceof CategoryPromotion) { ... } else if (promotion instanceof BulkPurchasePromotion) { ... }` (no `else` branch).<br>`PromotionRepository.loadAll`: `if (data[0].equals("PERCENTAGE")) { ... } else if (data[0].equals("CATEGORY")) { ... } else if (data[0].equals("BULK")) { ... }`. |
| Explanation | The promotion hierarchy is polymorphic for the calculation (`calculateDiscount`), but not for persistence: how each promotion type is written and rebuilt is decided with type tests in the repository. Each branch repeats the common fields (`id`, `name`, `startDate`, `endDate`). |
| Consequence | Adding a new promotion type requires editing both methods. If `saveAll` is not updated, promotions of the new type are silently not saved because there is no `else` branch. |
| Proposed solution | Same design as V-01 and V-04: one serializer per promotion type behind a common abstraction in the persistence package. The file format and the `PERCENTAGE`/`CATEGORY`/`BULK` tags stay identical. |
| Code from another team member | No |

### V-09

| Field | Content |
|---|---|
| ID | V-09 |
| Pattern | Protected Variations (guiding pattern); also Low Coupling |
| Module | Promotions (Req. 2) |
| Evidence | `PromotionRepository.loadAll() throws FileNotFoundException`.<br>`PromotionService`: `import java.io.*;` and every public method declares `throws FileNotFoundException` (`registerPercentageDiscount`, `registerCategoryDiscount`, `registerBulkPurchaseDiscount`, `listAllPromotions`, `listActivePromotions`, `findBestPromotionFor`, `findById`).<br>`SaleService.registerSale` also declares `throws FileNotFoundException`. |
| Explanation | `loadAll` already catches every `IOException` inside its `try/catch` and rethrows a `RuntimeException`, so `FileNotFoundException` is never thrown at runtime. Even so, the declaration exposes a file-storage detail in the service API and forces every caller to declare or handle it. |
| Consequence | Changing the storage mechanism (for example, to a database, one of the likely changes of this project) would force changes in the service signatures and in every caller, such as `SaleService.registerSale` and the promotion menu of `UI`. |
| Proposed solution | Remove the unnecessary `throws FileNotFoundException` declarations and the `java.io` imports from the service. Behavior does not change because the exception is never thrown. |
| Code from another team member | Yes: `SaleService.registerSale` and the promotion methods of `UI` (Technical Lead) declare or handle this exception; a `catch` of `FileNotFoundException` that can no longer be thrown would stop compiling and is removed by its owner. |

### V-10

| Field | Content |
|---|---|
| ID | V-10 |
| Pattern | Low Coupling (guiding pattern); also Indirection |
| Module | Returns (Req. 3) |
| Evidence | `ReturnRepository` (package `persistence`) imports `...service.ProductService` and `...service.SaleService`.<br>`ReturnRepository.loadAll`: `Sale originalSale = SaleService.findById(saleId);` and `Product product = ProductService.findById(productId);`.<br>The fields `saleService` and `productService`, their setters `setSaleService` / `setProductService` and the method `findSaleInService` are not used by `loadAll`. |
| Explanation | The persistence layer depends on a component of a higher layer and on static calls to two services, against the rule `ui → service → persistence → model`. The injected fields are dead code: two coupling mechanisms exist, and only the static one is used. |
| Consequence | `ReturnRepository` cannot be used without the services being initialized first, because their static state is set in their constructors. Any change to the `SaleService` or `ProductService` API breaks the repository, and a cycle between the service and persistence layers becomes possible. |
| Proposed solution | The repository must not know the services. Return only the stored data (ids) from the repository and resolve the `Sale` and `Product` references in `ReturnService`, the same shape as `SaleRepository` and `SaleService`. Remove the dead fields, setters and method. The file format stays identical. |
| Code from another team member | Yes: `Main` (Technical Lead) calls `setSaleService` and `setProductService`, and removes those calls last. Related to boundary candidate B-1 of `grasp-analysis.md` (Developer 2 changes first). |

### V-11

| Field | Content |
|---|---|
| ID | V-11 |
| Pattern | Low Coupling |
| Module | Returns (Req. 3) |
| Evidence | `ReturnService(ReturnRepository repository, SaleService saleService, ProductService productService)` assigns only `this.repository`; `saleService` and `productService` are ignored.<br>`ReturnService.findSaleById` and `generateMonthlyBalance`: `SaleService.getAllSales()`. `ReturnService.registerReturn`: `ProductService.restoreStock(product.getId(), 1)`. |
| Explanation | The constructor suggests dependency injection, but the class reaches the services through calls made by class name, so its real dependencies are hidden global state and cannot be replaced. |
| Consequence | The class cannot be tested or reused with other implementations, and it fails if the static state of `SaleService` has not been initialized by constructing it first. |
| Proposed solution | Keep the injected `SaleService` and `ProductService` in fields and call them through the instance references. This compiles with the current static methods, so it can be done first, and the Technical Lead removes `static` later without affecting this class. |
| Code from another team member | No for the first step. The Technical Lead later makes the `SaleService` methods instance methods (boundary candidate B-2 of `grasp-analysis.md`). |

### V-12

| Field | Content |
|---|---|
| ID | V-12 |
| Pattern | Information Expert (guiding pattern); also Low Coupling |
| Module | Returns (Req. 3) |
| Evidence | `ReturnService.viewReturnsByCustomer`: `if (returnItem.getSale().getCustomer().getId().equals(customerId))` |
| Explanation | The service navigates `Return → Sale → Customer → id` to decide who owns a return. The return, which holds the sale, is the one that should answer whether it belongs to a customer. |
| Consequence | If the way a sale references its customer changes, this method breaks, as would every other place that repeats the chain. |
| Proposed solution | Add a question method to `Return` (for example, whether it belongs to a customer id) and make the service call it. The results stay identical. |
| Code from another team member | Yes: `Return` belongs to Developer 1. |

### V-13

| Field | Content |
|---|---|
| ID | V-13 |
| Pattern | Polymorphism (guiding pattern); also Protected Variations |
| Module | Warranties (Req. 4) |
| Evidence | `WarrantyRepository.saveAll`: `if (warranty instanceof BasicWarranty) { type = "BASIC"; } else if (warranty instanceof ExtendedWarranty) { type = "EXTENDED"; } else { continue; }`.<br>`WarrantyRepository.loadAll`: `if (type.equals("BASIC")) { ... } else if (type.equals("EXTENDED")) { ... }`. |
| Explanation | The warranty hierarchy is polymorphic for duration, type name and cost, but not for persistence: the tag written to the file and the class rebuilt when reading are decided with type tests in the repository. |
| Consequence | Adding a new warranty type requires editing both methods, and the explicit `continue` in `saveAll` silently drops any warranty of a type that is not listed. |
| Proposed solution | Same design as V-01, V-04 and V-08: one entry per warranty type (tag and way to rebuild it) behind a common abstraction in the persistence package. The file format and the `BASIC`/`EXTENDED` tags stay identical. |
| Code from another team member | No |

### V-14

| Field | Content |
|---|---|
| ID | V-14 |
| Pattern | Low Coupling (guiding pattern); also Indirection |
| Module | Warranties (Req. 4) |
| Evidence | `WarrantyRepository` (package `persistence`) imports `...service.ProductService` and `...service.SaleService`.<br>`WarrantyRepository(ProductService productService)` calls `this("data/warranties.csv", null, productService);` and the sale service is set later with `setSaleService`.<br>`WarrantyRepository.findSaleById`: `for (Sale sale : saleService.getAllSales()) { ... }`; `WarrantyRepository.loadAll`: `productService.findById(data[2])`. |
| Explanation | Same problem as V-10: the persistence layer depends on a component of a higher layer. In addition, the repository is built in two steps (a `null` sale service first, a setter afterwards), and `getAllSales` is a static method invoked through an instance reference, which hides the static dependency. |
| Consequence | The repository is only valid after the setter has been called by the code that wires the application, and any change to the `SaleService` or `ProductService` API breaks it. It also uses a different injection mechanism from `ReturnRepository`. |
| Proposed solution | Same as V-10: the repository returns only the stored data (ids), and `WarrantyService` resolves the `Product` and `Sale` references. Remove the `null` argument and the setter. The file format stays identical. |
| Code from another team member | Yes: `Main` (Technical Lead) calls `setSaleService` and removes it last. Related to boundary candidate B-1 of `grasp-analysis.md` (Developer 2 changes first). |

### V-15

| Field | Content |
|---|---|
| ID | V-15 |
| Pattern | Information Expert (guiding pattern); also Low Coupling |
| Module | Returns (Req. 3) and Warranties (Req. 4) |
| Evidence | `ReturnService.findSaleById`: `for (Sale sale : SaleService.getAllSales()) { if (sale.getId().equals(saleId)) { return sale; } }`.<br>`WarrantyRepository.findSaleById`: the same loop over `saleService.getAllSales()`.<br>`ReturnRepository.loadAll` uses `SaleService.findById(saleId)` for the same purpose. |
| Explanation | Finding a sale by id is a responsibility of the class that stores and retrieves sales, `SaleService`, which already has `findById`. The lookup is reimplemented in two Developer 2 classes, and a third variant is used in another. |
| Consequence | The same lookup exists in three places with different code, and any change to how sales are searched has to be repeated. |
| Proposed solution | Use the single existing lookup of `SaleService` (`findById`) in all three places. Once V-10 and V-14 are corrected, the lookup only remains in `ReturnService` and `WarrantyService`. |
| Code from another team member | No |

### V-16

| Field | Content |
|---|---|
| ID | V-16 |
| Pattern | Information Expert |
| Module | Warranties (Req. 4) |
| Evidence | `WarrantyService.listWarrantiesExpiringSoon`: `LocalDate endDate = warranty.getEndDate(); if (!endDate.isBefore(today) && !endDate.isAfter(limit)) { ... }`.<br>`WarrantyService.findWarrantyByProduct`: `warranty.getProduct().getId().equals(productId) && warranty.getSale().getId().equals(saleId)`. |
| Explanation | `Warranty` already answers whether it is active (`isActive`), but the questions "does it expire soon" and "does it cover this product in this sale" are answered by the service, using the dates and the getter chains `Warranty → Product → id` and `Warranty → Sale → id`. |
| Consequence | If the way a warranty stores its dates, product or sale changes, the service breaks, and the expiry rule is separated from `isActive`, which lives in the warranty. |
| Proposed solution | Add two question methods to `Warranty` (one for the expiry window and one for coverage of a product in a sale) and make the service call them. The results stay identical. |
| Code from another team member | Yes: `Warranty` belongs to Developer 1. |

### V-17

| Field | Content |
|---|---|
| ID | V-17 |
| Pattern | Indirection (guiding pattern); also Protected Variations and Low Coupling |
| Module | Persons, Accessories, Promotions, Returns and Warranties |
| Evidence | `PersonService(PersonRepository repository)`, `AccessoryService(AccessoryRepository repository)`, `PromotionService(PromotionRepository repository)`, `ReturnService(ReturnRepository repository, ...)` and `WarrantyService(WarrantyRepository repository)`: each service keeps a field typed with the concrete file-based repository class, and no interface exists for any repository. |
| Explanation | The services and their storage are bound directly, and the storage is one of the likely changes of the project (a database instead of files). An interface between them does not exist. |
| Consequence | Changing the storage would require editing the type of the repository in the five services and the code that wires them, instead of adding one new implementation. |
| Proposed solution | Define one repository interface per module in the persistence package, implemented by the current file-based repositories, and make the services depend on the interface. Behavior and data files do not change, and `Main` keeps compiling because it passes the concrete repository where the interface is expected. |
| Code from another team member | No for the Developer 2 classes. `ProductService` (Developer 1) and `SaleService` (Technical Lead) have the same shape and are reported by their owners. |

## 5. Issues reported to the Technical Lead

| Class / method | Owner | Description and evidence |
|---|---|---|
| `SaleService.registerSale` and `buildSale` (accessories) | Technical Lead | `Accessory` is a `Product`, but it is stored and queried through a parallel service and repository, so `SaleService` has to branch: `if (product == null) { product = (Product) accessoryService.findById(productId); }` and `if (product instanceof Accessory) { accessoryService.update((Accessory) product); } else { productService.update(product); }`. On the Developer 2 side, `AccessoryService.findById` and `update` mirror the `ProductService` API with a different type. Related to boundary candidate B-4. |
| Static API of `SaleService` and `ProductService` | Technical Lead / Developer 1 | `SaleService` keeps its collaborators in `static` fields and exposes `static` methods (`getAllSales`, `findById`). `ProductService` keeps its repository in a `static` field and exposes `static` `findById` and `update`, while `restoreStock` is an instance method. This is the origin of V-10, V-11 and V-14. The Developer 2 callers that use class names are `ReturnService` (`SaleService.getAllSales`, `ProductService.restoreStock`) and `ReturnRepository` (`SaleService.findById`, `ProductService.findById`); `WarrantyRepository` already uses an instance reference. Related to B-2. |
| Answers to open VERIFY items of `grasp-analysis.md` | Technical Lead | **D-3:** `WarrantyService.assignExtendedWarranty` returns the warranty and does not touch the sale total, so nothing in Developer 2 code adds the additional cost.<br>**D-5:** `AccessoryService.registerAccessory` saves the full list (`repository.saveAll`) before returning, so any change made to the entity afterwards, such as setting compatible consoles, is not persisted unless `update` is called.<br>**D-8:** `WarrantyService.assignBasicWarranty` and `assignExtendedWarranty` persist immediately (`repository.saveAll`), before `SaleService` calls `saleRepository.save(sale)`.<br>**Monthly balance hot spot:** `ReturnService.generateMonthlyBalance` does not recompute the final total; it uses `sale.calculateTotal()` (see Section 6). |
| `ProductService.registerConsole` | Developer 1 / Technical Lead | The method has no call to `ProductValidator.validateProductData`, while `registerVideoGame` has it. See Section 6. |

## 6. Possible functional defects and items outside scope

These items were found by reading the code. They change what the user or the data files show if corrected, so they are not corrected in this requirement and should be reproduced before they are handled.

| Class / method | Evidence | Observed behavior / reason |
|---|---|---|
| Warranties of the same sale (`WarrantyRepository.loadAll`, `WarrantyService.assign...`, `SaleService.registerSale`) | `WarrantyRepository.loadAll`: `Sale sale = findSaleById(data[3]); ... if (product == null \|\| sale == null) continue;`. `WarrantyService.assignBasicWarranty` / `assignExtendedWarranty`: `repository.loadAll()`, `add`, `repository.saveAll(...)`. `SaleService.registerSale` creates the warranties before `saleRepository.save(sale)`. Related to D-8. | The sale is not stored yet when the second warranty of the same sale is created, so the first one is skipped when loading and is not written back by `saveAll`. A sale with two warranties (two consoles, or a console with an extended warranty) would keep only the last one. |
| `ReturnService.generateMonthlyBalance` | `totalSales += sale.calculateTotal();` and `totalReturns += returnItem.getRefundAmount();` | `calculateTotal()` sums product prices only, so the balance does not subtract promotion discounts (and, according to D-2, sales reloaded from the file do not carry the discount), nor does it include warranty costs (D-3). Correcting it changes the figures shown. |
| `PersonRepository`, `AccessoryRepository`, `PromotionRepository`, `ReturnRepository` (`saveAll` / `loadAll`) | Fields are joined with `","` and read with `line.split(",")`. The free-text fields are `name`, `title` and the return `reason`. | A value containing a comma shifts the fields when the record is read back, or truncates the return reason. |
| `ProductService.registerConsole` | `registerConsole` creates the `Console` directly, while `registerVideoGame` calls `ProductValidator.validateProductData` first, and the JavaDoc of both says they validate. | A console with invalid data is accepted and a video game is rejected. Adding the validation would reject inputs that are accepted today. |
| Error handling and console output of Accessories, Promotions, Returns and Warranties | `AccessoryRepository`, `PromotionRepository`, `ReturnRepository`, `WarrantyRepository`: `throw new RuntimeException("Error saving ...", e)`. `PromotionRepository`, `ReturnRepository` and `WarrantyRepository`: `ensureFileExists` only prints `System.err`. `AccessoryService` and `ReturnService`: `throw new IllegalArgumentException(...)` with English messages (for example, `"A return must include at least one product"`). | These classes were not covered by the Req. 6 refactor, so their errors do not use the custom exception hierarchy, and three repositories write to the console outside `UI`. The Req. 3 specification also asks for Spanish messages in `ReturnService`. Changing the exception types, the messages or the `System.err` output changes what the program shows or emits, which the team's verification plan treats as behavior to preserve. |
