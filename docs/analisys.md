**1. What attributes are common to all people interacting 
with the store, and which ones are specific to each specific 
type of person? How is this distinction reflected in a class 
hierarchy?**

Answer: The common attributes for all people are name, 
identification number, and contact phone. The customer type 
has a purchase history and an email. The employee/vendor type 
has an employee code and an assigned work shift. This 
distinction is reflected by placing the common attributes in 
the parent class Person, which inherits them to the child 
classes, thereby avoiding code duplication.

**2. Should there be a class representing a "generic person" 
without specifying their role? Why or why not? What implication 
does this decision have on the possibility of instantiating 
said class?**

Answer: No, since in this business context there will never 
be a person who does not fulfill a specific function. This 
decision implies creating the parent class Person as an abstract 
class so that it can only be an abstract entity and cannot be 
instantiated, simply allowing other classes to inherit from it.

**3. What characteristics do all products commercialized by 
the store have in common, regardless of their type? What 
characteristics are specific to each product type?**

Answer: The common attributes of the product class are 
identifier, title, price, and available stock quantity. The 
video game product type has attributes for platform type, 
genre, and age rating. The console product type has attributes 
for brand, model, and generation.

**4. Each product type must be able to present a description 
that integrates its particular characteristics. How should this 
behavior be declared in the base class to ensure that all 
subclasses implement it in their own way? What object-oriented 
mechanism allows this?**

Answer: This behavior should be declared as an abstract method 
without a body or implementation inside the parent class. The 
object-oriented mechanism that allows this is called polymorphism.

**5. A sale involves a customer, a vendor, and one or more 
products. What type of relationships exist between the class 
representing the sale and the other classes of the system? Are 
these relationships of inheritance, association, composition, 
or another type? Justify.**

Answer: The relationship between the vendor and sale classes 
is aggregation with a one-to-many cardinality; the sale and 
customer classes have an aggregation relationship with a 
one-to-many cardinality; and the sale and product classes have 
an aggregation relationship with a many-to-many cardinality.

**6. Should the sale be responsible for calculating its own 
total, or should this responsibility fall on another class? 
Argue your decision.**

Answer: The Sale class should be responsible for calculating 
its own total because the class that performs an action is the 
one that has the information to do so. If an external class were 
created, the Sale class would have to pass its attributes, which 
would break encapsulation.

**7. How is it guaranteed in the design that a sale cannot be 
registered without at least one product? At what point in the 
system should this validation rule be validated?**

Answer: It is guaranteed that a sale cannot be registered 
without products through the constructor located in the model 
class and through the method that processes the sale in the 
service layer.

**8. How is the automatic inventory update reflected in the 
design when a sale is registered? What classes are involved 
in this operation?**

Answer: The layered architecture design establishes that 
services coordinate transactions. When a sale is registered 
through SaleService, it validates the business rules. Once the 
sale is confirmed, the system loops through the acquired products 
and executes a stock update method before moving on to the data 
persistence layer.

**9. The system must be organized into four capas: model, 
persistence, services, and user interface. What type of classes 
belong to each layer? What criterion allows deciding which layer 
a class should be placed in?**

Answer: Business domain classes representing entities and their 
hierarchies belong to the model layer. File management and 
information storage classes to ensure data is preserved between 
executions belong to the persistence layer. Classes containing 
business rules, validation logic, and system operation coordination 
are inside the service layer. Finally, classes in charge of user 
interaction are within the ui layer.

**10. Why should the logic of saving and retrieving data from 
files not be inside the domain classes? What problems are generated 
when these responsibilities are mixed?**

Answer: Mixing persistence logic with domain classes violates 
the principle of separation of responsibilities established in the 
system's layered architecture, causing the business model classes 
to be tied to specific storage media, drastically reducing 
scalability. Furthermore, the dependency rule is broken, as the 
model must be completely independent and should not know about or 
depend on any other layer.

**11. What dependencies are allowed between layers and which are 
prohibited? Justify the meaning of the allowed dependencies.**

Answer: The allowed dependencies are: the ui layer can depend on 
the service layer; the service layer can depend on the model and 
persistence layers; and finally, the persistence layer can depend 
on the model layer. The prohibited dependencies are: the model layer 
cannot depend on any other layer; the ui layer is forbidden from 
accessing the persistence layer without going through the service 
layer first; and finally, the model layer is forbidden from containing 
file access logic. Establishing that the model layer is independent 
allows entities to be independent of how data is stored, permitting 
changes to the storage mechanism at any time without affecting the 
main entities. Allowing the service layer to act as an intermediary 
between the user interface and persistence ensures that all business 
validations are executed in a controlled manner and are not exposed 
or fragmented in interface menus or data files. By structuring 
dependencies from top to bottom, a modular, decoupled, and easily 
testable design by modules is achieved.