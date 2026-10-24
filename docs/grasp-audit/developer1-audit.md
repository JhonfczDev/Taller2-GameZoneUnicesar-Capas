# GRASP Audit — Developer 1

**Audited commit of `develop`:** `d1d7cccc5d0b53bd3ad395cd6368b0c75ae4a056`  
**Auditor:** Developer 1  
**Phase:** 1 — Audit

The verdict criteria and the evidence rules are the ones agreed by the team in `docs/grasp-analysis.md`. Responsibilities follow the assignment table of Requirement 7, and `TEAM.md` matches it.

## 1. Scope

| Module (requirement) | Classes and methods audited |
|---|---|
| Products (Workshop 1) | `Product`, `VideoGame`, `Console`, `ProductRepository`, `ProductService` (excluding `restoreStock`). |
| Accessories (Req. 1) | `Accessory`, `Controller`, `Cable`, `Memory`. |
| Promotions (Req. 2) | `Promotion`, `PercentageDiscount`, `CategoryDiscount`, `BulkPurchaseDiscount`, `PromotionService`, `PromotionRepository`. |
| Returns (Req. 3) | `Return`, `Sale.canBeReturned`. |
| Warranties (Req. 4) | `Warranty`, `BasicWarranty`, `ExtendedWarranty`. |
| Exceptions and validations (Req. 6) | The Req. 6 modifications and classes inside the `exceptions` and `validation` packages developed by Developer 1 (`ProductValidator`, custom business exceptions, etc.). |

---

## 2. Verdict summary

| Pattern | Verdict       | Violation IDs    |
|---|---------------|------------------|
| Information Expert | Violation     | V-D1-01, V-D1-02 |
| Creator | Correct       | -                |
| Controller | Not Aplicable | -                |
| Low Coupling | Violation     | V-D1-03          |
| High Cohesion | Correct       | -                |
| Polymorphism |               |                  |
| Pure Fabrication |               |                  |
| Indirection |               |                  |
| Protected Variations |               |                  |

---

## 3. Pattern sections

### 3.1 Information Expert
**Verdict:** Violation (V-D1-01, V-D1-02).

*Correct points.* `Product`, `VideoGame`, and `Console` manage their own attributes and calculate specific values inherent to their state. `Promotion` and its subclasses correctly calculate their respective discounts (`calculateDiscount(Sale sale)`).

*Violations.* `ProductRepository` contains business evaluation logic regarding stock thresholds instead of delegating it fully to `Product`. Furthermore, `Return` evaluates return eligibility rules inside `Sale.canBeReturned` by querying external states rather than keeping self-contained validation rules inside the domain models.

### 3.2 Creator
**Verdict:** Correct.

Factories and services such as `ProductService` correctly instantiate domain objects (`VideoGame`, `Console`) from primitive parameters received from the UI layer. Aggregate roots properly instantiate their immediate components.

### 3.3 Controller
**Verdict:** Not applicable.

None of the classes under Developer 1's responsibility receive raw system input events or handle user interface scanners directly; UI event management is handled strictly by the `UI` class (Technical Lead).

### 3.4 Low Coupling
**Verdict:** Violation (V-D1-03).

*Violation.* The `Return` class and `Sale.canBeReturned` introduce tight coupling between the returns module and the product/sale states, creating bidirectional awareness across module boundaries.

### 3.5 High Cohesion
**Verdict:** Correct.

The domain classes (`Product`, `Accessory`, `Promotion`, `Warranty`, `Return`) and validation classes (`ProductValidator`) maintain a single, focused responsibility clearly describable without conjunctions.



---

## 4. Violations

### V-D1-01

| Field | Content |
|---|---|
| ID | V-D1-01 |
| Pattern | Information Expert |
| Module | Products (Workshop 1) |
| Evidence | `ProductService` checks stock availability levels directly via external getter combinations instead of invoking a domain method on `Product`. |
| Explanation | The responsibility of verifying stock sufficiency belongs to the `Product` entity itself, as it holds the direct data (`stockQuantity`). |
| Consequence | Business logic regarding inventory rules is scattered across service layers, making rules harder to maintain. |
| Proposed solution | Move the stock verification logic into a boolean method inside `Product` (e.g., `hasSufficientStock(int requestedQuantity)`). |
| Code from another team member | No |

### V-D1-02

| Field | Content |
|---|---|
| ID | V-D1-02 |
| Pattern | Information Expert |
| Module | Returns (Req. 3) |
| Evidence | `Sale.canBeReturned()` accesses nested attributes across multiple objects to validate return windows and conditions. |
| Explanation | `Sale` lacks encapsulated helper methods to evaluate its own return eligibility, delegating or forcing deep property inspection. |
| Consequence | High vulnerability to null-pointer exceptions and structural coupling if data schemas change. |
| Proposed solution | Refactor return validation criteria into self-contained methods inside `Sale` and `Return`. |
| Code from another team member | Yes: `Sale` belongs to the Technical Lead. |

### V-D1-03

| Field | Content |
|---|---|
| ID | V-D1-03 |
| Pattern | Low Coupling |
| Module | Returns (Req. 3) and Products |
| Evidence | `Return` class imports and references `Sale` and `Product` directly with deep getter chains. |
| Explanation | High inter-module entanglement between returns, sales, and products increases maintenance friction. |
| Consequence | Changes in product or sale identifiers cascade into breaking adjustments in the return evaluation logic. |
| Proposed solution | Apply Law of Demeter delegation principles (such as `isForCustomer` or direct domain queries) to isolate object interaction. |
| Code from another team member | Yes: Involves `Sale` (Technical Lead). |

