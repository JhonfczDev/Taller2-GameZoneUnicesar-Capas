flowchart TB

    subgraph UI["UI Layer (ui package)"]
        direction TB
        ConsoleMenu["ConsoleMenu"]
    end

    subgraph SERVICE["Service Layer (service package)"]
        direction TB
        PersonService["PersonService"]
        ProductService["ProductService"]
        SaleService["SaleService"]
    end

    subgraph PERSISTENCE["Persistence Layer (persistence package)"]
        direction TB
        PersonRepository["PersonRepository"]
        ProductRepository["ProductRepository"]
        SaleRepository["SaleRepository"]
    end

    subgraph MODEL["Model Layer (model package)"]
        direction TB
        Person["Person «abstract»"]
        Customer["Customer"]
        Seller["Seller"]
        Product["Product «abstract»"]
        VideoGame["VideoGame"]
        Console["Console"]
        Sale["Sale"]
    end

    Main["Main (root package)"]

    UI -->|depends on| SERVICE
    SERVICE -->|depends on| PERSISTENCE
    SERVICE -->|depends on| MODEL
    PERSISTENCE -->|depends on| MODEL

    Main -.->|creates / wires| UI
    Main -.->|creates / wires| SERVICE
    Main -.->|creates / wires| PERSISTENCE

    classDef modelClass fill:#1b5e20,stroke:#0d3d13,stroke-width:2px,color:#ffffff;
    classDef persistenceClass fill:#0d47a1,stroke:#08306b,stroke-width:2px,color:#ffffff;
    classDef serviceClass fill:#e65100,stroke:#a03800,stroke-width:2px,color:#ffffff;
    classDef uiClass fill:#b71c1c,stroke:#7f1313,stroke-width:2px,color:#ffffff;
    classDef mainClass fill:#4a148c,stroke:#31095c,stroke-width:2px,color:#ffffff;

    class Person,Customer,Seller,Product,VideoGame,Console,Sale modelClass;
    class PersonRepository,ProductRepository,SaleRepository persistenceClass;
    class PersonService,ProductService,SaleService serviceClass;
    class ConsoleMenu uiClass;
    class Main mainClass;