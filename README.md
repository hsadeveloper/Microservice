# E Commerce Store
#### Table of Contents
  [Background](#Background) 
- [Modules](#Modules) 
    - [Store Service](#store-service) 
    - [User Service](#user-service)  
    - [Payment Service](#payment-service) 
    - [Cart Service](#cart-service) 
    - [Shipment Service](#shipment-service) 
    - Service Registry
- Entity Relationship for Store Service
- Microservices Architecture
- API Endpoints
- RabbitMQ Integration
- Dependencies
- API Endpoints
- Error Handling
- Contributing
- License
  
##### Background
  - 💁 The E-Store backend application is designed to manage the operations of an e-commerce platform, providing services related to products,  orders, payments, inventory, users, and shipments.
    
    - 💁 Whole project
        * 🔶 Config-server -
        * 🔶 API Gateway -
        * 🔶  Queue somewhere between services
          
   ##### Modules       
       ###### store-service
        * ✅ holds all product-related information and consists of five entities: Product, Department, Manufacturer, and Inventory.
        * ✅  It contains dedicated controllers, repositories, and services for each entity.
        * ✅ A SQL file is included for inserting data into the corresponding MySQL tables.
        * 🔶  Need to improve performance by applying the CQRS Microservice method
        

    ###### 💁 user-service
        * ✅   The user-service holds all user-related information and consists of three entities: user, role, userRole.
        * ✅   It contains dedicated controllers, repositories, and services for each entity.
        * 🔴   JWT config file is not complete.
        * 🔴   Authorization is required to restrect access.
        * ✅   The service uses MongoDB (NoSQL) and it inserts data into the database as a dump of data.
        * 🔶   save users to actual disk (Not on docker)
        * 🔴   Thymeleaf pages for sign-in, Registration, and others.

    ###### 💁 payment-service
        * 🔶   The payment service holds some payment-related information and consists of one entity: ChargeRequest.
        * 🔶   It contains dedicated controllers, repositories, and services for each entity.
        * 🔴   Payment methods need to be integrated, such as Stripe or others.
        * 🔴   Need to pull user information from JWT.
     
    ###### 💁 cart-service
        * 🔶   The cart-service holds some cart-related information and consists of two entities: cart, and cartItem.
        * 🔶   It contains dedicated controllers, repositories, and services for each entity.
        * 🔴   Checkout functionality needs to be added and integrated with the payment methods.
     
    - 💁 shipment-service
        * 🔶   The shipment service holds some shipment-related information: not that much
        * 🔶   Currently, it is a template service
        * 🔶   need to read from the queue.

---
###### Legends:
* ✅ Works as expected (might need to be tweaked)
* 🔶 Partially implemented 
* 🔴 Require to be implemented
    
---
###### Working In Progress
* Working on one feature at a time. Trying to enable authenticated users to add, modify, and delete items in the cart service. Eventually, users   will be able to check out and pay using Stripe, QR code, or other payment methods.
* Search needs to be improved, such as searching for an item
---

###### Technology stack
Clearly list the core technologies used in the project, providing a quick reference for anyone looking at the codebase.
* Framework: Spring Boot
* Language: Java 17
* Build Tool: Gradle
* Relational Database: MySQL
* NoSQL Database: MongoDB
* Architectural Patterns: Microservice Design, CQRS
  
---
 
###### Prerequisites
* Outline the software and tools developers need to have installed on their machine to run the project.
* Java Development Kit (JDK) v17 or later
* A code editor or IDE (e.g., IntelliJ, VS Code)
* A database client for MySQL and MongoDB (optional)
---

### Modules

- Store Service - Like Catalog
- Order Service
- Payment Service
- Shipment Service
- User Service
- Service Registry 

---

#### Entity Relationship for Store Service
<p align="center">
  <img src=images/entity-relationships.png height=500 width=800>
</p >

---
#### Use Case

<p align="center">
    <img src=images/usecase_1.png height=800 >
</p >

---

### API Endpoints
- [Store - sevice endpoints](http://localhost:1727/swagger-ui/index.html#/)

- [User - sevice endpoints](http://localhost:1737/swagger-ui/index.html#/)

- [Order - sevice endpoints](http://localhost:8010/swagger-ui/index.html#/)

- [Payment - sevice endpoints](http://localhost:8088/swagger-ui/index.html#/)

- [Shipment - sevice endpoints](http://localhost:9087/swagger-ui/index.html#/)



##### What is RabbitMQ
a distributed message broker that collects streaming data from multiple sources to route it to different destinations for processing.


##### What is used for?

RabbitMQ is used for asynchronous communication between microservices. Each service publishes and subscribes to events through RabbitMQ, ensuring loose coupling and reliable message delivery.


𝙎𝙮𝙣𝙘𝙝𝙧𝙤𝙣𝙤𝙪𝙨 𝘾𝙤𝙢𝙢𝙪𝙣𝙞𝙘𝙖𝙩𝙞𝙤𝙣 vs A𝙎𝙮𝙣𝙘𝙝𝙧𝙤𝙣𝙤𝙪𝙨 𝘾𝙤𝙢𝙢𝙪𝙣𝙞𝙘𝙖𝙩𝙞𝙤𝙣

1. 𝙎𝙮𝙣𝙘𝙝𝙧𝙤𝙣𝙤𝙪𝙨 𝘾𝙤𝙢𝙢𝙪𝙣𝙞𝙘𝙖𝙩𝙞𝙤𝙣: Real-time interaction, often through HTTP requests or RPC mechanisms. Simple but may introduce coupling.

2. 𝘼𝙨𝙮𝙣𝙘𝙝𝙧𝙤𝙣𝙤𝙪𝙨 𝘾𝙤𝙢𝙢𝙪𝙣𝙞𝙘𝙖𝙩𝙞𝙤𝙣: Decoupled communication via message brokers or event buses. Promotes loose coupling and scalability.
