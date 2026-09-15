```mermaid
classDiagram
    namespace Model {
        class Person {
            <<abstract>>
            -String id
            -String name
            -String phone
            +getId() String
            +getName() String
            +getPhone() String
        }

        class Customer {
            -String email
            -List~Sale~ purchaseHistory
            +getEmail() String
            +getPurchaseHistory() List~Sale~
            +addPurchas(Sale sale) void
        }

        class Seller {
            -String employeeCode
            -String workShift
            +getEmployeeCode() String
            +getWorkShift() String
        }

        class Product {
            <<abstract>>
            -String id
            -String title
            -double price
            -int stockQuantity
            +getId() String
            +getTitle() String
            +getPrice() double
            +getStockQuantity() int
            +setStockQuantity(int quantity) void
            +getDescription() String
        }

        class VideoGame {
            -String platform
            -String genre
            -String ageRating
            +getDescription() String
        }

        class Console {
            -String brand
            -String model
            -String generation
            +getDescription() String
        }

        class Sale {
            -String id
            -LocalDate date
            -Customer customer
            -Seller seller
            -List~Product~ products
            +Sale(String id, LocalDate date, Customer customer, Seller seller, List~Product~ products)
            +getId() String
            +getDate() LocalDate
            +getCustomer() Customer
            +getSeller() Seller
            +getProducts() List~Product~
            +setId(String id) void
            +setDate(LocalDate date) void
            +setCustomer(Customer customer) void
            +setSeller(Seller seller) void
            +setProducts(List~Product~ products) void
            +calculateTotal() double
            +addProduct(Product product) void
            +printSale() String
        }
    }

    Person <|-- Customer
    Person <|-- Seller
    Product <|-- VideoGame
    Product <|-- Console

    Sale "0..*" --> "1" Customer
    Sale "0..*" --> "1" Seller
    Sale o-- Product : 1..*

    namespace Service {
        class ProductService {
            -ProductRepository repository
            +registerProduct(Product p) void
            +getAllProducts() List~Product~
            +updateStock(String id, int qty) void
        }

        class PersonService {
            -PersonRepository repository
            +registerCustomer(Customer c) void
            +getAllCustomers() List~Customer~
            +getAllSellers() List~Seller~
            -initializeDefaultSellers() void
        }

        class SaleService {
            -SaleRepository saleRepository
            -ProductService productService
            -PersonService personService
            +SaleService(SaleRepository saleRepository, ProductService productService, PersonService personService)
            +registerSale(String customerId, String sellerId, List~String~ productIds) void
            +getAllSales() List~Sale~
            +getSalesByCustomer(String customerId) List~Sale~
            +getSalesBySeller(String sellerId) List~Sale~
            -buildSaleFromLine(String line) Sale
            -buildSale(String id, LocalDate date, String customerId, String sellerId, List~String~ productIds) Sale
            -generateSaleId() String
        }
    }

    namespace Persistence {
        class ProductRepository {
            -File file
            +saveAll(List~Product~ products) void
            +loadAll() List~Product~
        }

        class PersonRepository {
            -File file
            +saveAll(List~Person~ persons) void
            +loadAll() List~Person~
        }

        class SaleRepository {
            -String FILE_PATH
            -String DELIMITER
            -String PRODUCT_SEPARATOR
            +SaleRepository()
            +save(Sale sale) void
            +saveAll(List~Sale~ sales) void
            +findAllLines() List~String~
            -toLine(Sale sale) String
            -ensureFileExists() void
        }
    }

    namespace UI {
        class UI {
            -Scanner scanner
            -SaleService saleService
            -PersonService personService
            -ProductService productService
            +launch() void
            +productMenu() void
            +personMenu() void
            +saleMenu() void
            +registerVideoGame() void
            +registerConsole() void
            +listAllProducts() void
            +registerCustomer() void
            +listAllCustomers() void
            +listAllSellers() void
            +registerSale() void
            +listAllSales() void
            +salesByClient() void
            +salesBySeller() void
        }
    }

    ProductService --> ProductRepository
    PersonService --> PersonRepository
    SaleService --> SaleRepository
    SaleService --> ProductService
    SaleService --> PersonService
    UI --> ProductService
    UI --> PersonService
    UI --> SaleService
```
