# AI Usage Log — Developer 2

## Project

**Project:** GameZone Unicesar  
**Course:** Programming III  
**Role:** Developer 2 — Person Module  
**AI tool used:** ChatGPT

## Purpose of this log

This document records the use of Artificial Intelligence during the development of the GameZone Unicesar workshop. The AI was used mainly to clarify programming concepts, understand Git commands, review code written by the student, explain errors, and improve commit and Pull Request wording.

The final implementation and design decisions were made by the student and the development team. The AI responses were used as guidance and were reviewed before being applied.

---

## Interaction 1 — Understanding the project structure

**Topic:** Starting the Person module and layered architecture.

**Question / request made to the AI:**  
I asked where to begin implementing the `Person` class and the rest of the Person module, considering the four-layer architecture and my responsibility as Developer 2.

**AI assistance:**  
The AI explained that the Person module should be developed from the domain model toward persistence and then services. It explained the role of the `model`, `persistence`, and `service` layers and how the classes should communicate.

**How I used the response:**  
I used the explanation to organize my work according to the architecture required by the workshop. I started with the `Person` model and later continued with persistence and services.

**Type of AI use:**  
Conceptual clarification about object-oriented programming and layered architecture.

---

## Interaction 2 — Abstract Person class

**Topic:** Common attributes and inheritance.

**Question / request made to the AI:**  
I asked how the abstract `Person` class should be understood and what was meant by placing the common attributes in the base class.

**AI assistance:**  
The AI explained that `Person` represents the common characteristics shared by `Customer` and `Seller`, such as identification, name, and phone. It also explained why an abstract base class cannot be instantiated directly and how the subclasses inherit its common attributes.

**How I used the response:**  
I used the explanation to understand the inheritance relationship and to implement the common attributes in `Person`.

**Type of AI use:**  
Conceptual clarification about inheritance and abstract classes.

---

## Interaction 3 — Customer and purchase history

**Topic:** `Customer` and the relationship with `Sale`.

**Question / request made to the AI:**  
I asked about the `purchaseHistory` attribute in `Customer`, specifically whether it should be a `List<Sale>` and how it relates to the `Sale` class.

**AI assistance:**  
The AI explained that a customer's purchase history can be represented as a `List<Sale>` and described the conceptual relationship between `Customer` and `Sale`. It also explained that persistence of sales can be handled separately from persistence of people.

**How I used the response:**  
I used the explanation to understand the relationship and to keep the responsibilities of the Person and Sale modules separated.

**Type of AI use:**  
Conceptual clarification and review of the student's model design.

---

## Interaction 4 — PersonRepository constructors

**Topic:** Constructors in the persistence layer.

**Question / request made to the AI:**  
I asked why to use or not use two constructors in `PersonRepository`: one without arguments and another that received a file path.

**AI assistance:**  
The AI explained constructor overloading and the use of:

`this("data/persons.txt");`

I recommend using two constructors to delegate from the default constructor to the constructor that receives the path. He explained that this allows for a default file location while also allowing for a custom path when needed.

**How I used the response:**  
I used the explanation to understand the existing repository structure and why the constructors were organized this way.

**Type of AI use:**  
Conceptual clarification about Java constructors.

---

## Interaction 5 — `ensureFileExists()`

**Topic:** File creation in the persistence layer.

**Question / request made to the AI:**  
I asked for an appropriate commit message for the `ensureFileExists()` method.

**AI assistance:**  
The AI identified the purpose of the method: checking whether the parent directory and data file exist and creating them when necessary. It suggested the Conventional Commit message:

`feat: implement ensureFileExists method`

**How I used the response:**  
I used the suggestion as a reference for naming the commit according to the workshop's Conventional Commits requirement.

**Type of AI use:**  
Suggestion for an English identifier/commit description and Git guidance.

---

## Interaction 6 — `saveAll()` method

**Topic:** Person persistence.

**Question / request made to the AI:**  
I asked for a suitable commit message for the first implementation of `saveAll()` in `PersonRepository`.

**AI assistance:**  
The AI suggested a commit message describing the implementation of the method, for example:

`feat: implement saveAll method in PersonRepository`

It also recommended checking `git status` before committing.

**How I used the response:**  
I used the suggestion to create an atomic commit focused specifically on the `saveAll()` functionality.

**Type of AI use:**  
Git guidance and commit-message suggestion.

---

## Interaction 7 — Reviewing `PersonService`

**Topic:** Filtering customers and sellers.

**Question / request made to the AI:**  
I provided my `PersonService` code and asked about the implementation of `getAllCustomers()` and `getAllSellers()`.

**AI assistance:**  
The AI reviewed the code and identified that the loop was checking and casting the list itself instead of the individual `Person` object. It explained that the correct logic should use `person instanceof Customer` and cast `person`, and similarly for `Seller`.

**How I used the response:**  
I used the explanation to correct my own code and understand how `instanceof` and casting work with polymorphism.

**Type of AI use:**  
Review of student-written code and explanation of a programming error.

---

## Interaction 8 — `findCustomerById()` and `findSellerById()`

**Topic:** Search methods in the Person module.

**Question / request made to the AI:**  
I discussed the `findCustomerById()` and `findSellerById()` methods and how they fit into the persistence and service layers.

**AI assistance:**  
The AI explained the separation of responsibilities between the repository and service layers and helped me document these methods as part of the Person module functionality.

**How I used the response:**  
I used the explanation to keep the search functionality within the appropriate layer and to describe the work accurately in the Pull Request.

**Type of AI use:**  
Conceptual clarification and code-organization guidance.

---

## Interaction 9 — Git branches

**Topic:** Creating feature branches from `develop`.

**Question / request made to the AI:**  
I asked how to create a new branch starting from the current `develop` branch.

**AI assistance:**  
The AI explained the sequence:

`git checkout develop`  
`git pull origin develop`  
`git checkout -b feature/<branch-name>`

It explained why `develop` should be updated before creating the feature branch.

**How I used the response:**  
I used these commands to follow the Git Flow required by the workshop.

**Type of AI use:**  
Explanation of Git commands.

---

## Interaction 10 — Naming the AI documentation branch

**Topic:** Branch naming for the AI usage log.

**Question / request made to the AI:**  
I asked how the branch for adding the AI usage log should be named.

**AI assistance:**  
The AI suggested using a documentation-oriented branch name such as:

`docs/ai-usage-log`

**How I used the response:**  
I used the suggestion as a naming reference for the branch containing the AI usage documentation.

**Type of AI use:**  
Git guidance and naming suggestion.

---

## Interaction 11 — Pull Request for the Person persistence module

**Topic:** Pull Request title and description.

**Question / request made to the AI:**  
I asked for an English title and description for the Pull Request containing the Person persistence work, including `PersonRepository`, `ensureFileExists()`, `saveAll()`, `findCustomerById()`, and `findSellerById()`.

**AI assistance:**  
The AI helped organize the Pull Request description into a summary, changes, and purpose, and suggested the title:

`feat: implement person persistence`

**How I used the response:**  
I used the wording as a reference to describe the work performed in the branch clearly and consistently.

**Type of AI use:**  
Documentation wording and GitHub Pull Request guidance.

---

## Interaction 12 — Pull Request for the Person service module

**Topic:** Pull Request title and description.

**Question / request made to the AI:**  
I asked for an English Pull Request title and description for the Person service branch, including customer registration, customer and seller listing, and the search methods by ID.

**AI assistance:**  
The AI helped organize the description and suggested the title:

`feat: implement person service layer`

**How I used the response:**  
I used the suggestion to document the completed functionality of the service branch.

**Type of AI use:**  
Documentation wording and GitHub Pull Request guidance.

---

## Interaction 13 — Query about creating branches

**Topic:** Branch creation

**Question / request made to the AI:**  
For the changes I need to make in the project, is it correct to create a single branch for several related changes, or should I create separate branches for each functionality?

**AI assistance:**  
The AI explained that, following a feature-based workflow, it is recommended to create separate branches when the changes correspond to different functionalities or responsibilities. When several changes are part of the same functionality and are closely related, they can remain in the same branch. The AI also recommended making atomic commits to facilitate review through Pull Requests.

**How I used the response:**  
The AI was used to properly organize the work into branches and determine when changes should be separated into different branches, while maintaining a workflow consistent with the requirements of the assignment.

**Type of AI use:**  
Clarification of the use of the branches.

---

## Interaction 14 — Implementing `AccessoryRepository` within the layered architecture

**Topic:** Accessory persistence while respecting the layered architecture.

**Question / request made to the AI:**  
How can I implement the `AccessoryRepository` class to store accessories in a text file while keeping the project's layered architecture?

**AI assistance:**  
The AI explained that the class belongs to the persistence layer, depends only on the model layer (`Accessory`, `Controller`, `Cable`, `Memory`), and must not contain business logic. It described the structure already used in the project: a file attribute, two constructors (one with the default path and one that receives a path), a private method that creates the folder and file when missing, a save method that writes one line per accessory with a type discriminator in the first column resolved through `instanceof`, and an initialization method that preloads default data when the file is empty. It also stressed that model classes must never include file-access logic and that the service layer is the one that uses the repository.

**How I used the response:**  
I used it to verify that my repository followed the same structure as `PersonRepository` and `ProductRepository` and did not mix responsibilities between layers.

**Type of AI use:**  
Conceptual clarification about layered architecture and persistence.

---

## Interaction 15 — Implementing `loadAll()` for accessories

**Topic:** Reading the file and rebuilding objects.

**Question / request made to the AI:**  
How can I implement a `loadAll()` method that reads accessories from a text file, separates their attributes, and converts each record into an `Accessory` object?

**AI assistance:**  
The AI explained the general flow: return an empty list if the file does not exist; read the file line by line; skip blank lines; split each line into its fields; use the first field as a discriminator to decide which subclass to build; convert numeric fields to numbers; and add each object to the result list. Because the constructors of `Controller` and `Memory` do not receive the compatible consoles (this is an inherited attribute of `Accessory`, not a constructor parameter), it explained that the object should be built first without that data and that the compatible consoles should be assigned afterwards through the inherited setter.

**How I used the response:**  
I used it to correct how `Controller` and `Memory` are built in `loadAll` and in the default-data initialization.

**Type of AI use:**  
Correction of existing code and explanation of how a file is traversed and parsed.

---

## Interaction 16 — Handling empty fields when splitting records

**Topic:** Optional attributes when reading the file.

**Question / request made to the AI:**  
How can I handle empty fields when reading accessory records using a split with a negative limit, and avoid errors when an attribute is optional?

**AI assistance:**  
The AI explained that a plain split silently discards trailing empty fields, so a record whose last column is empty ends up with fewer elements than expected. Using a negative limit keeps those empty trailing fields, so the number of elements always matches the number of columns and the indexes do not shift. It also recommended guarding access to optional fields with a length check and making sure the index checked is the same index that is read, because in the original code the length condition for the controller did not match the field it actually accessed.

**How I used the response:**  
I used it to review the indexes in `loadAll` and to understand why an empty optional field can cause errors.

**Type of AI use:**  
Explanation of a `String.split` detail and debugging guidance.

---

## Interaction 17 — Implementing `AccessoryService`

**Topic:** Registering accessories while delegating storage.

**Question / request made to the AI:**  
How can I implement the `AccessoryService` class to register accessories and delegate data storage to `AccessoryRepository`?

**AI assistance:**  
The AI explained that, following the `ProductService` pattern, the service receives the repository through its constructor, and that each registration method creates the object, loads the current list, appends the new accessory, and saves the whole list again. The service never opens files itself: every disk operation goes through the repository.

**How I used the response:**  
I used it as a reference to keep the same structure as `ProductService`.

**Type of AI use:**  
Conceptual clarification about the service layer.

---

## Interaction 18 — Finding an accessory by ID from the service

**Topic:** Delegating lookups to the repository.

**Question / request made to the AI:**  
How can I implement a method to find an accessory by its ID using the repository, without accessing the file directly from the service?

**AI assistance:**  
The AI explained that the service should simply call the repository's lookup method, which already iterates over the loaded accessories and compares ids. This way the service knows nothing about the file format and uses no file-reading classes, which respects the dependency direction from service to persistence.

**How I used the response:**  
I used it to verify that the service contained no file-access logic.

**Type of AI use:**  
Conceptual clarification about separation of responsibilities between layers.

---

## Interaction 19 — Validating price and stock

**Topic:** Validations before saving.

**Question / request made to the AI:**  
How can I validate that an accessory's price and stock are valid values before saving it?

**AI assistance:**  
The AI explained that business validations belong in the service layer and must run before the object is created and saved, throwing an `IllegalArgumentException` with a clear message, as `SaleService.registerSale` does. It suggested a single reusable private validation method that rejects prices that are zero or negative and stock quantities that are negative, so the same rule applies to every accessory type.

**How I used the response:**  
I used it as a reference to decide where to place the validations and how to word the error messages.

**Type of AI use:**  
Guidance on data validation and exception handling.

---

## Interaction 20 — Implementing `PromotionRepository` and recovering data at startup

**Topic:** Promotion persistence.

**Question / request made to the AI:**  
How can I implement the `PromotionRepository` class to store promotions in a text file and recover their data when the system starts?

**AI assistance:**  
The AI explained that the repository uses `data/promotions.csv`, creates the file if it does not exist, and exposes `saveAll` and `loadAll`. Since there are three concrete promotion types, each line carries a discriminator that `loadAll` uses to decide which subclass to rebuild. Data is recovered at startup because the services call `loadAll` every time they need the promotions, and if the file does not exist an empty list is returned. It also reminded me that the assignment requires preloading at least three promotions in the CSV, one of each type, with validity dates that include the day of the tests.

**How I used the response:**  
I used it to understand how data is recovered when the system starts and to prepare the preloaded file.

**Type of AI use:**  
Conceptual clarification about persistence.

---

## Interaction 21 — Storing a promotion's attributes in a single line

**Topic:** CSV format and object reconstruction.

**Question / request made to the AI:**  
How can I store a promotion's attributes in a single comma-separated line so I can rebuild the object when reading the file?

**AI assistance:**  
The AI explained that the common fields (id, name, start date, end date) should be written first, followed by the fields specific to each promotion type, so that the discriminator tells the loader how many columns to expect and how to interpret them. It also explained that a date converted to text uses the ISO format, which can be parsed back into a date object when loading, and warned that promotion names must not contain commas because they would break the column structure.

**How I used the response:**  
I used it as a reference to define the format of each line in the CSV.

**Type of AI use:**  
Explanation of serialization formats.

---

## Interaction 22 — `save()` and `loadAll()` while separating model and persistence

**Topic:** Separation between the model and persistence layers.

**Question / request made to the AI:**  
How can I implement the `save()` and `loadAll()` methods in `PromotionRepository` while respecting the separation between the model and persistence?

**AI assistance:**  
The AI explained that the model classes (`Promotion` and its subclasses) contain only attributes and business logic and know nothing about files. The repository is the one that reads the object's data through getters when saving and calls the constructors when loading. It also clarified that the assignment specifies a `saveAll` method over a list rather than a single-object `save`, and that saving one promotion is resolved in the service by loading the list, adding the new promotion, and saving the full list again.

**How I used the response:**  
I used it to verify that the repository was the only class with file access.

**Type of AI use:**  
Conceptual clarification about layers.

---

## Interaction 23 — Validating start and end dates

**Topic:** Date validation when registering promotions.

**Question / request made to the AI:**  
How can I validate that a promotion's start date is before or equal to its end date?

**AI assistance:**  
The AI explained that the validation belongs in the service's registration methods and must run before the promotion is created, rejecting any end date that falls before the start date and also rejecting missing dates. It clarified that the delivered version of `PromotionService` did not include this validation and that it can be added as a small private method reused by the three registration methods.

**How I used the response:**  
I used it to decide to add the validation and to word the error message.

**Type of AI use:**  
Guidance on data validation.

---

## Interaction 24 — Determining whether a promotion is active

**Topic:** Using `LocalDate` for validity.

**Question / request made to the AI:**  
How can I implement a method that determines whether a promotion is active using `LocalDate` and its validity dates?

**AI assistance:**  
The AI explained that the before and after comparisons on `LocalDate` are strict, so negating them makes the range limits inclusive: a promotion is active when the reference date is not before the start date and not after the end date. This check lives in the `Promotion` class, and the service applies it using the current date when it lists the active promotions.

**How I used the response:**  
I used it to understand how inclusive limits work and to review how active promotions are listed.

**Type of AI use:**  
Explanation of date handling with `java.time`.

---

## Interaction 25 — Final price with a percentage promotion

**Topic:** Discount calculation without negative prices.

**Question / request made to the AI:**  
How can I calculate a product's final price when a percentage promotion is applied, preventing the discount from producing a negative price?

**AI assistance:**  
The AI explained that the discount is the price multiplied by the percentage divided by one hundred, that the percentage should be validated to be between 0 and 100, and that the final price should be bounded below by zero so the result can never be negative. It also clarified that in the assignment's design the discount is calculated over the total of the sale through the promotion's discount method, so a per-product calculation is a variation of that design.

**How I used the response:**  
I used it to understand the formula and the validation of the percentage range.

**Type of AI use:**  
Conceptual clarification and calculation logic.

---

## Interaction 26 — Storing several product IDs in a single field

**Topic:** Serializing lists inside a single column.

**Question / request made to the AI:**  
How can I store several product IDs associated with a return in a single field, using `|` as the separator?

**AI assistance:**  
The AI explained that the ids of the returned products are collected into a list of strings, joined with the chosen separator, and written as one column of the CSV. It clarified that the delivered version of `ReturnRepository` uses `;` instead, which works the same way, and that `|` is also valid as long as it is escaped when reading the field back.

**How I used the response:**  
I used it to compare both separators and understand the format of the products column.

**Type of AI use:**  
Explanation of list serialization.

---

## Interaction 27 — `String.join()` and splitting by `|`

**Topic:** Writing and reading the products field.

**Question / request made to the AI:**  
How can I use `String.join()` to store several product IDs and then recover them using `split("\\|")`?

**AI assistance:**  
The AI explained that `String.join` takes a delimiter and a collection of strings and returns them concatenated with that delimiter, and that when reading, `split` receives a regular expression in which the pipe character means "or". That is why it must be escaped (or wrapped with a quoting utility). It noted that `AccessoryRepository` already applies the same technique for compatible console ids, and recommended treating an empty field separately so that no phantom empty id is produced.

**How I used the response:**  
I used it to understand why the separator needs escaping and to relate it to the accessory repository.

**Type of AI use:**  
Explanation of basic regular expressions in `split`.

---

## Interaction 28 — `loadAll()` with dates and products resolved by ID

**Topic:** Rebuilding related objects.

**Question / request made to the AI:**  
How can I implement `loadAll()` to convert dates stored as text into `LocalDate` objects and recover the related products through their IDs?

**AI assistance:**  
The AI explained that parsing the stored text with `LocalDate.parse` works because saving a `LocalDate` as text produces the ISO format. For the relationships, the repository receives `SaleService` and `ProductService` in its constructor: the sale is found by scanning all sales (because `SaleService` has no lookup by id), and each product is resolved by its id, skipping the ones that no longer exist. It also recommended using the negative-limit split to preserve empty fields.

**How I used the response:**  
I used it to review how references and dates are rebuilt when loading.

**Type of AI use:**  
Explanation of rebuilding objects from text.

---

## Interaction 29 — Validating that returned products exist before registering the return

**Topic:** Pre-validations in `registerReturn`.

**Question / request made to the AI:**  
How can I validate that the products included in a return exist before registering the operation?

**AI assistance:**  
The AI explained that every validation must run before any stock change or persistence, so a failure leaves no partial effects: the product list must not be empty, the sale must exist, the sale must be within the 30-day window, and each product id must correspond to a product that actually belongs to that sale, resolved from the sale's own product list. Any failure throws an `IllegalArgumentException` with a message in Spanish, as the assignment requires.

**How I used the response:**  
I used it to verify the order of the validations relative to the other operations.

**Type of AI use:**  
Guidance on business-rule validation.

---

## Interaction 30 — Returning units to inventory

**Topic:** Reusing stock logic.

**Question / request made to the AI:**  
How can I implement the logic to return to inventory the units of the products accepted in a return?

**AI assistance:**  
The AI explained that `ReturnService` should not modify stock directly. Instead it should call `ProductService.restoreStock`, the additive method assigned to the Technical Lead, so the stock-update logic is not duplicated. It described how that method would work conceptually: find the product, reject unknown products and non-positive quantities, increase the stock by the given quantity, and persist the change through the existing product update operation. In `ReturnService` it is called once per returned product, after validating and before persisting the return.

**How I used the response:**  
I used it to understand why `restoreStock` is reused and at which point of the flow it is called.

**Type of AI use:**  
Conceptual clarification about reusing methods across layers.

---

## Interaction 31 — Preserving empty fields when splitting records

**Topic:** Reading warranty records.

**Question / request made to the AI:**  
How can I use a split with a negative limit to preserve the empty fields of warranty records when reading them?

**AI assistance:**  
The AI explained that, with a negative limit, the split keeps trailing empty fields, so the number of elements always equals the number of columns. It recommended checking the length of each parsed record before reading it and discarding incomplete lines instead of letting an index error interrupt the whole load.

**How I used the response:**  
I used it to understand how to avoid index misalignment when reading the file.

**Type of AI use:**  
Explanation of a `String.split` detail.

---

## Interaction 32 — Missing file and malformed records

**Topic:** Error handling in `loadAll`.

**Question / request made to the AI:**  
How can I handle the errors that occur when a warranties file does not exist or contains records with an incorrect format?

**AI assistance:**  
The AI described three levels of handling. If the file does not exist, `loadAll` returns an empty list, and the file is created when the repository is constructed. Read errors are wrapped into an unchecked exception. Malformed records are handled by wrapping the parsing of each line in its own error handling that reports the problem to the error stream and continues with the next line, as `SaleService` does when it builds a sale from a line. Records whose sale or product can no longer be resolved are also skipped.

**How I used the response:**  
I used it to understand how to prevent one damaged line from stopping the rest of the file from loading.

**Type of AI use:**  
Guidance on exception handling.

---

## Interaction 33 — `listWarrantiesExpiringSoon(int daysAhead)`

**Topic:** Warranties about to expire.

**Question / request made to the AI:**  
How can I implement a `listWarrantiesExpiringSoon(int daysAhead)` method that returns the warranties that will expire within a given number of days using `LocalDate`?

**AI assistance:**  
The AI explained that the limit date is obtained by adding the requested number of days to today's date, and that a warranty is included when its end date is neither before today nor after that limit, which excludes warranties that have already expired. It also recommended validating that the number of days is not negative.

**How I used the response:**  
I used it to review the filter condition and its edge cases.

**Type of AI use:**  
Explanation of date handling with `java.time`.

---

## Interaction 34 — Using `isBefore()`, `isAfter()`, and `plusDays()`

**Topic:** Validity and proximity to expiration.

**Question / request made to the AI:**  
How can I use `isBefore()`, `isAfter()`, and `plusDays()` to determine whether a warranty is active and whether it is about to expire?

**AI assistance:**  
The AI explained that `isBefore` and `isAfter` are strict comparisons that do not include equal dates, so negating them produces an inclusive range; that `plusDays` returns a new date instead of modifying the original because `LocalDate` is immutable; and that a warranty is active if today falls within its range, and about to expire if, in addition, its end date falls on or before the date obtained by adding the requested days to today.

**How I used the response:**  
I used it to understand inclusive limits and the immutability of `LocalDate`.

**Type of AI use:**  
Conceptual explanation about `java.time`.

---

## Interaction 35 — Validating that the end date is not before the start date

**Topic:** Warranty date validation.

**Question / request made to the AI:**  
How can I validate that a warranty's end date is after or equal to its start date before registering the warranty?

**AI assistance:**  
The AI explained that in this design the end date is not received as a parameter: the base `Warranty` class calculates it in its constructor by adding the duration (6 or 12 months) to the start date, so it is always later than the start date. The validation would only make sense if the end date came from outside, in which case an end date before the start date would be rejected with an `IllegalArgumentException`. What is worth validating in the assignment methods of the service is that the product, the sale, and the start date are not null.

**How I used the response:**  
I used it to decide which validations to add in the service.

**Type of AI use:**  
Guidance on validation and design.

---

## Git workflow followed

The workshop requires feature branches to be derived from `develop`, atomic commits to be pushed immediately, and Pull Requests to be reviewed before merging. The AI was used to clarify these Git operations and naming conventions.

The relevant workflow was:

```bash
git checkout develop
git pull origin develop
git checkout -b feature/<feature-name>
```

For commits, the project follows Conventional Commits, using prefixes such as `feat:`, `fix:`, `docs:`, `refactor:`, and `chore:`.

The AI was used to understand and apply these conventions, not to replace the development work.

---

## Summary of AI usage

During the Person module development, AI assistance was mainly used for:

- Understanding inheritance, abstract classes, polymorphism, and layered architecture.
- Understanding the responsibilities of the model, persistence, and service layers.
- Reviewing student-written code and explaining errors.
- Understanding Java constructors and file handling.
- Clarifying the use of `instanceof` and casting.
- Understanding Git commands and the feature-branch workflow.
- Suggesting English commit messages following Conventional Commits.
- Helping organize Pull Request titles and descriptions.

The AI was not used to generate the complete system design or to replace the student's implementation of the Person module. The project decisions, coding, testing, and understanding of the resulting code remained the student's responsibility.
