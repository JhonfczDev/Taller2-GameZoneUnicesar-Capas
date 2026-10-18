# GRASP Audit — Developer 2

**Audited commit of `develop`:** `d1d7cccc5d0b53bd3ad395cd6368b0c75ae4a056`  
**Auditor:** Developer 2  
**Phase:** 1 — Audit

The verdict criteria and the evidence rules are the ones agreed by the team in `docs/grasp-analysis.md`. In this repository the console class is `UI` (package `ui`), so `UI` is used everywhere in this report. Responsibilities follow the assignment table, and `../../TEAM.md` matches it.

## 1. Scope

| Module (requirement) | Classes and methods audited |
|---|---|
| Persons (Workshop 1) | `Person`, `Customer` (including `getPurchaseHistory`, `addPurchase`), `Seller`.<br>`PersonRepository`: `saveAll`, `loadAll`, `findCustomerById`, `findSellerById`, `ensureFileExists`, `initializeDefaultSellers`.<br>`PersonService`: `registerCustomer`, `getAllCustomers`, `getAllSellers`, `findCustomerById`, `findSellerById`. |
| Accessories (Req. 1) | `AccessoryRepository`: `saveAll`, `loadAll`, `findById`, `joinConsoleIds`, `splitConsoleIds`, `ensureFileExists`, `initializeDefaultAccessories`.<br>`AccessoryService`: `registerController`, `registerCable`, `registerMemory`, `getAllAccessories`, `getAccessoriesByType`, `getCompatibleAccessories`, `findById`, `hasStock`, `update`. |
| Promotions (Req. 2) | `PromotionRepository`: `saveAll`, `loadAll`, `ensureFileExists`.<br>`PromotionService`: `registerPercentageDiscount`, `registerCategoryDiscount`, `registerBulkPurchaseDiscount`, `listAllPromotions`, `listActivePromotions`, `findBestPromotionFor`, `findById`. |

## 2. Verdict summary

| Pattern | Verdict | Violation IDs |
|---|---|---|
| Information Expert | Violation | (V-05 related) |
| Creator | Violation | V-07 |
| Controller | Not applicable | — |
| Low Coupling | Violation | V-03, (V-09, V-10 related) |
| High Cohesion | Violation | V-02, V-06 |
| Polymorphism | Violation | V-01, V-04, V-05, V-08 |
| Pure Fabrication | Correct | — |
| Indirection | Violation | V-10 |
| Protected Variations | Violation | V-09 (V-01, V-04, V-08, V-10 related) |

When a finding breaks more than one pattern, it is registered once under its guiding pattern and referenced as "related" under the others.

## 3. Pattern sections

### 3.1 Information Expert
**Verdict:** Violation (V-05 related).

*Correct points.* `Person`, `Customer` and `Seller` own and expose their own data. `PromotionService.findBestPromotionFor` asks each promotion for its own discount (`promotion.calculateDiscount(sale)`), and `PromotionService.listActivePromotions` asks each promotion whether it is active (`isActive(today)`).

*Violations.* `AccessoryService.getCompatibleAccessories` decides on data that belongs to the accessory (V-05).

### 3.2 Creator
**Verdict:** Violation (V-07).

*Correct points.* `PersonService.registerCustomer`, `PromotionService.register...Discount` create the objects they record from the primitives they receive. `Customer` creates its own `purchaseHistory` because it contains it. The repositories (`PersonRepository`, `AccessoryRepository`, `PromotionRepository`) create objects in `loadAll` from the file fields, since they hold the initializing data. The creation of the default sellers and accessories is legitimate as an act of creation; the concern about where that seeding lives is registered under High Cohesion (V-02, V-06).

*Violations.* `AccessoryService.registerController`, `registerCable` and `registerMemory` receive already-built entities, so the caller has to create them (V-07).

### 3.3 Controller
**Verdict:** Not applicable.

Classes reviewed: `Person`, `Customer`, `Seller`, `PersonRepository`, `PersonService`, `AccessoryRepository` and `AccessoryService`. None of them receives events from the user interface, and none uses `Scanner` or `System.out`. System events are received by `UI` (Technical Lead), which delegates to the services.

### 3.4 Low Coupling
**Verdict:** Violation V-03, (V-10 related).

*Correct points.* The services receive their repositories through the constructor and never instantiate them, and the Persons, Accessories and Promotions repositories depend only on the model.

*Violations.* The cyclic dependency between `Customer` and `Sale` (V-03), and the file-storage exception exposed by the promotion service signatures (V-09). The services are directly bound to concrete file-based repository classes (V-10).

### 3.5 High Cohesion
**Verdict:** Violation (V-02, V-06).

`Person`, `Seller`, `PersonService`, `AccessoryService` and `PromotionService` can each be described in one sentence without "and". The violations are the seed data inside the Persons and Accessories repositories (V-02, V-06).

### 3.6 Polymorphism
**Verdict:** Violation (V-01, V-04, V-05, V-08).

*Correct point.* `PromotionService.findBestPromotionFor` works with the abstract `Promotion` and calls `calculateDiscount` without checking the promotion subclass.

*Violations.* The type tests in `PersonRepository` and `PersonService` (V-01), in `AccessoryRepository` (V-04), in `AccessoryService` (V-05), and in `PromotionRepository` (V-08).

### 3.7 Pure Fabrication
**Verdict:** Correct.

File layout, parsing and writing live in dedicated repository classes, so the model classes (`Person`, `Customer`, `Seller` and the others) contain no I/O code. `AccessoryRepository.joinConsoleIds` and `splitConsoleIds` keep the storage format of the compatible-console list inside the persistence layer. Validation lives in `PersonValidator`, called from `PersonService`, and `ProductRepository`, `PersonRepository` and `SaleRepository` convert `IOException` into `PersistenceException`.

The preparation of the data file (`ensureFileExists`) is repeated in each repository. It is not registered because it sits in the class that should own it, so it does not break the criterion.

### 3.8 Indirection
**Verdict:** Violation (V-10).

*Correct points.* Callers obtain people, accessories and promotions through their services instead of reading files, and the repositories sit between the services and the files.

*Violations.* `PersonService`, `AccessoryService` and `PromotionService` are bound directly to their concrete file-based repository classes (V-10).

### 3.9 Protected Variations
**Verdict:** Violation V-09, (V-01, V-04, V-08, V-10 related).

The unprotected variation points are the storage mechanism, whose `java.io` exception leaks into the promotion service signatures (V-09), and the addition of new person, accessory or promotion types, whose type-dependent conditionals are registered under Polymorphism (V-01, V-04, V-08). The direct dependency on concrete repositories is registered under Indirection (V-10).

*Correct points.* `PromotionService.findBestPromotionFor` does not change when a new promotion type is added, and the repository constructors that receive a `filePath` allow changing the file location without touching the classes.

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
| Pattern | Indirection (guiding pattern); also Protected Variations and Low Coupling |
| Module | Persons, Accessories and Promotions |
| Evidence | `PersonService(PersonRepository repository)`, `AccessoryService(AccessoryRepository repository)` and `PromotionService(PromotionRepository repository)`: each service keeps a field typed with the concrete file-based repository class, and no interface exists for any of these repositories. |
| Explanation | The services and their storage are bound directly, and the storage is one of the likely changes of the project (a database instead of files). An interface between them does not exist. |
| Consequence | Changing the storage would require editing the repository type in the three services and the code that wires them, instead of adding one new implementation. |
| Proposed solution | Define one repository interface per module in the persistence package, implemented by the current file-based repositories, and make the services depend on the interface. Behavior and data files do not change, and `Main` keeps compiling because it passes the concrete repository where the interface is expected. |
| Code from another team member | No for the Developer 2 classes. `ProductService` (Developer 1) and `SaleService` (Technical Lead) have the same shape and are reported by their owners. |

## 5. Issues reported to the Technical Lead

| Class / method | Owner | Description and evidence |
|---|---|---|
| `SaleService.registerSale` and `buildSale` (accessories) | Technical Lead | `Accessory` is a `Product`, but it is stored and queried through a parallel service and repository, so `SaleService` has to branch: `if (product == null) { product = (Product) accessoryService.findById(productId); }` and `if (product instanceof Accessory) { accessoryService.update((Accessory) product); } else { productService.update(product); }`. On the Developer 2 side, `AccessoryService.findById` and `update` mirror the `ProductService` API with a different type. Related to boundary candidate B-4. |
| Answers to open VERIFY items of `grasp-analysis.md` | Technical Lead | **D-5:** `AccessoryService.registerAccessory` saves the full list (`repository.saveAll`) before returning, so any change made to the entity afterwards, such as setting compatible consoles, is not persisted unless `update` is called. |

## 6. Possible functional defects and items outside scope

These items were found by reading the code. They change what the user or the data files show if corrected, so they are not corrected in this requirement and should be reproduced before they are handled.

| Class / method | Evidence | Observed behavior / reason |
|---|---|---|
| `PersonRepository`, `AccessoryRepository`, `PromotionRepository` (`saveAll` / `loadAll`) | Fields are joined with `","` and read with `line.split(",")`. The free-text fields are `name` and `title`. | A value containing a comma shifts the fields when the record is read back. |
| Error handling and console output of Accessories and Promotions | `AccessoryRepository` and `PromotionRepository`: `throw new RuntimeException("Error saving ...", e)`. `PromotionRepository.ensureFileExists` only prints `System.err`. `AccessoryService` throws `IllegalArgumentException(...)` with English messages. | These classes were not covered by the Req. 6 refactor, so their errors do not use the custom exception hierarchy, and `PromotionRepository` writes to the console outside `UI`. Changing the exception types, the messages or the `System.err` output changes what the program shows or emits, which the team's verification plan treats as behavior to preserve. |
