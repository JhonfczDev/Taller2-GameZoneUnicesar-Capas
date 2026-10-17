# GRASP Audit — Developer 2

**Audited commit of `develop`:** `d1d7cccc5d0b53bd3ad395cd6368b0c75ae4a056`  
**Auditor:** Developer 2  
**Phase:** 1 — Audit

The verdict criteria and the evidence rules are the ones agreed by the team in `docs/grasp-analysis.md`. In this repository the console class is `UI` (package `ui`), so `UI` is used everywhere in this report. Responsibilities follow the assignment table, and `../../TEAM.md` matches it.

## 1. Scope

| Module (requirement) | Classes and methods audited |
|---|---|
| Persons (Workshop 1) | `Person`, `Customer` (including `getPurchaseHistory`, `addPurchase`), `Seller`.<br>`PersonRepository`: `saveAll`, `loadAll`, `findCustomerById`, `findSellerById`, `ensureFileExists`, `initializeDefaultSellers`.<br>`PersonService`: `registerCustomer`, `getAllCustomers`, `getAllSellers`, `findCustomerById`, `findSellerById`. |

## 2. Verdict summary

| Pattern | Verdict | Violation IDs |
|---|---|---|
| Information Expert | Correct | — |
| Creator | Correct | — |
| Controller | Not applicable | — |
| Low Coupling | Violation | V-03 (V-04 related) |
| High Cohesion | Violation | V-02 |
| Polymorphism | Violation | V-01 |
| Pure Fabrication | Correct | — |
| Indirection | Violation | V-04 |
| Protected Variations | Violation | V-01 (V-04 related) |

When a finding breaks more than one pattern, it is registered once under its guiding pattern and referenced as "related" under the others.

## 3. Pattern sections

### 3.1 Information Expert
**Verdict:** Correct for the Persons module.

*Correct points.* `Person`, `Customer` and `Seller` own and expose their own data.

### 3.2 Creator
**Verdict:** Correct for the Persons module.

*Correct points.* `PersonService.registerCustomer` creates the object it records from the primitives it receives. `Customer` creates its own `purchaseHistory` because it contains it. `PersonRepository` creates objects in `loadAll` from the file fields, since it holds the initializing data. The creation of the default sellers is legitimate as an act of creation; the concern about where that seeding lives is registered under High Cohesion (V-02).

### 3.3 Controller
**Verdict:** Not applicable.

Classes reviewed: `Person`, `Customer`, `Seller`, `PersonRepository` and `PersonService`. None of them receives events from the user interface, and none uses `Scanner` or `System.out`. System events are received by `UI` (Technical Lead), which delegates to the services.

### 3.4 Low Coupling
**Verdict:** Violation (V-03; V-04 related).

*Correct points.* `PersonRepository` depends only on the model, and `PersonService` receives its repository through the constructor and never instantiates it.

*Violations.* The cyclic dependency between `Customer` and `Sale` (V-03).

### 3.5 High Cohesion
**Verdict:** Violation (V-02).

`Person`, `Seller` and `PersonService` can each be described in one sentence without "and". The violation is the seed data inside the Persons repository (V-02).

### 3.6 Polymorphism
**Verdict:** Violation (V-01).

*Violations.* The type tests in `PersonRepository` and `PersonService` (V-01).

### 3.7 Pure Fabrication
**Verdict:** Correct.

File layout, parsing and writing live in `PersonRepository`, so the model classes (`Person`, `Customer`, `Seller`) contain no I/O code. Validation lives in `PersonValidator`, called from `PersonService`.

The preparation of the data file (`ensureFileExists`) is repeated in each repository. It is not registered because it sits in the class that should own it, so it does not break the criterion.

### 3.8 Indirection
**Verdict:** Violation (V-04).

*Correct points.* Callers obtain people through `PersonService` instead of reading files, and `PersonRepository` sits between the service and the files.

*Violations.* `PersonService` is bound directly to the concrete file-based `PersonRepository` class (V-04).

### 3.9 Protected Variations
**Verdict:** Violation (V-01; V-04 related).

The unprotected variation point is the addition of new person types, whose type-dependent conditionals are registered under Polymorphism (V-01). The direct dependency on the concrete `PersonRepository` is registered under Indirection (V-04).

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
| Pattern | Indirection (guiding pattern); also Protected Variations and Low Coupling |
| Module | Persons |
| Evidence | `PersonService(PersonRepository repository)`: the service keeps a field typed with the concrete file-based repository class, and no interface exists for the repository. |
| Explanation | The service and its storage are bound directly, and the storage is one of the likely changes of the project (a database instead of files). An interface between them does not exist. |
| Consequence | Changing the storage would require editing the type of the repository in `PersonService` and the code that wires it, instead of adding one new implementation. |
| Proposed solution | Define a repository interface for the Persons module in the persistence package, implemented by the current file-based repository, and make `PersonService` depend on the interface. Behavior and data files do not change, and `Main` keeps compiling because it passes the concrete repository where the interface is expected. |
| Code from another team member | No for the Developer 2 classes. |


## 5. Issues reported to the Technical Lead

No Persons-specific issue from this section is included here; the original section's reported items concern other modules or cross-module behavior outside the Persons (Workshop 1) audit.

## 6. Possible functional defects and items outside scope

| Class / method | Evidence | Observed behavior / reason |
|---|---|---|
| `PersonRepository` (`saveAll` / `loadAll`) | Fields are joined with `","` and read with `line.split(",")`. The free-text field is `name`. | A value containing a comma shifts the fields when the record is read back. |
