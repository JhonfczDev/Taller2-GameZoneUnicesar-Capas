Markdown
# Team Information - GameZoneUnicesar

## 👥 Team Members

| Role | Full Name | Assigned Module | Feature Branch |
| :--- | :--- | :--- | :--- |
| **Technical Leader** | Jhon Fredy Chinchilla Zapata | Sales & Integration | `main` / `develop` |
| **Developer 1** | Elkin Jímenez Lara | Products | `feature/product-module` |
| **Developer 2** | Sebastián Flores Gómez | Persons | `feature/person-module` |

---

## 🧩 Class Distribution

* **Technical Leader (5 Classes):**
  1. `Sale` (Domain class)
  2. `SaleRepository` (File-based persistence)
  3. `SaleService` (Business rules & stock validation)
  4. `Menu` (Console User Interface)
  5. `Main` (Application entry point & dependency injection)

* **Developer 1 (5 Classes):**
  1. `Product` (Abstract base class for products)
  2. `VideoGame` (Derived class for video games)
  3. `Console` (Derived class for consoles)
  4. `ProductRepository` (File-based persistence)
  5. `ProductService` (Business rules & stock update)

* **Developer 2 (5 Classes):**
  1. `Person` (Abstract base class for persons)
  2. `Client` (Derived class for clients)
  3. `Vendor` (Derived class for vendors)
  4. `PersonRepository` (File-based persistence)
  5. `PersonService` (Business rules & management)

---

## 📋 Committed Activities

### Technical Leader Activities
- [ ] Create the project repository on GitHub with initial configuration (`README.md`, `.gitignore`).
- [ ] Configure project branches (`main` and `develop`) and set up branch protection rules.
- [ ] Set up the Maven project with initial `pom.xml` and the four-layer package structure.
- [ ] Create and maintain the `TEAM.md` file with team details and class distribution.
- [ ] Implement the `Sale` domain class with attributes, constructor, and basic methods.
- [ ] Implement the sale total calculation method.
- [ ] Implement the `SaleRepository` persistence class.
- [ ] Implement the `SaleService` class with validation rules (minimum one product, stock check, inventory update).
- [ ] Implement the main menu structure for the User Interface.
- [ ] Implement submenus for each of the three modules.
- [ ] Implement the `Main` class with initial data loading and dependency injection.
- [ ] Review and merge developers' Pull Requests into the integration branch.
- [ ] Create the final `README.md` with compilation and execution instructions.

### Developer 1 Activities (`feature/product-module`)
- [ ] Create the feature branch for the product module.
- [ ] Implement the abstract base class `Product` with common attributes, constructor, and common methods.
- [ ] Declare the abstract description method to be implemented by derived classes.
- [ ] Implement the `VideoGame` derived class with specific attributes and description implementation.
- [ ] Implement the `Console` derived class with specific attributes and description implementation.
- [ ] Implement the `ProductRepository` persistence class for saving and loading from files.
- [ ] Implement the `ProductService` class with registration, listing, and stock update methods.
- [ ] Document all module classes with JavaDoc in English.
- [ ] Open and request a Pull Request to the Technical Leader for module integration.

### Developer 2 Activities (`feature/person-module`)
- [ ] Create the feature branch for the person module.
- [ ] Implement the abstract base class `Person` with common attributes, constructor, and common methods.
- [ ] Declare the abstract business method to be implemented by derived classes.
- [ ] Implement the `Client` derived class with specific attributes.
- [ ] Implement the `Vendor` derived class with specific attributes.
- [ ] Implement the `PersonRepository` persistence class for saving and loading from files.
- [ ] Implement the `PersonService` class with registration and listing methods.
- [ ] Document all module classes with JavaDoc in English.
- [ ] Open and request a Pull Request to the Technical Leader for module integration.
