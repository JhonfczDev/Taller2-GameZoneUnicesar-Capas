# AI Usage Log - Developer 1

## Project

GameZoneUnicesar

## Role

Developer 1 - Product Module

## AI Tools

* Gemini
* ChatGPT



## 1. Class Diagram - Gemini

### Purpose

The class diagram for the project was developed with assistance from Gemini.

### AI Assistance

Gemini was used to analyze the project requirements and assist in defining the structure of the classes, attributes, methods, inheritance relationships, and dependencies between the different layers of the application.

The resulting diagram includes the main components of the project:

* Model
* Service
* Persistence
* UI

It also represents the relationships between the Product hierarchy and the services and repositories involved in the system.

### Developer Decision

The diagram was reviewed and adapted according to the project requirements and the implementation of the project.

The prompts and interactions used to create and refine the diagram were carried out in Gemini.

---

## 2. ProductRepository - ChatGPT

### Purpose

ChatGPT was used to obtain guidance about the structure and responsibilities of the `ProductRepository` class.

### AI Assistance

The AI explained that `ProductRepository` should belong to the persistence layer and be responsible for saving and loading product information from a file.

The main operations considered were:

* `saveAll(List<Product> products)`
* `loadAll()`
* `findById(String id)`
* `update(Product product)`

### Developer Decision

The persistence mechanism will use a text file. The implementation will be adapted to the existing classes and project structure.

---

## 3. ProductService - ChatGPT

### Purpose

ChatGPT was used to understand the structure and responsibilities of `ProductService`.

### AI Assistance

The AI explained that `ProductService` should communicate with `ProductRepository` and handle the operations related to products.

The operations considered include:

* Registering video games.
* Registering consoles.
* Getting all products.
* Finding a product by its ID.
* Updating a product.

### Developer Decision

The service will be implemented according to the project requirements and the existing model classes.

---

## 4. Review of Existing Product Classes - ChatGPT

### Purpose

ChatGPT was used to review the Product classes that had already been implemented.

### AI Assistance

The AI reviewed the structure of:

* `Product`
* `VideoGames`
* `Console`

The review focused on inheritance, private attributes, constructors, getters, setters, and the abstract `getDescription()` method.

### Developer Decision

The existing implementation was maintained and will be adjusted only when necessary according to the project requirements.

---

## 5. Developer Responsibility

The developer is responsible for implementing, adapting, testing, and understanding the final code used in the project.

AI tools were used as support during the development process, particularly for analyzing the project structure, reviewing the class design, and clarifying implementation concepts.

The final implementation was reviewed and adapted to the project's existing code and requirements.
