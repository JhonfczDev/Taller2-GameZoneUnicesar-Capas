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
