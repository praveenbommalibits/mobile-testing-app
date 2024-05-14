# mobile-testing-app

This README provides essential information for setting up, deploying, and running the application.

## Table of Contents

1. Introduction
2. Prerequisites
3. Clone & Building
4. Configuration
5. Deployment
6. Running the Application
7. API Documentation
8. UML Diagrams
9. Reflection

## Introduction

mobile-testing-app application facilitates the testing engineer to list down all the mobiles and avail option
book and return the mobile device for testing.

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 17
- Maven 3.x
- github & github actions
- Azure 

## Clone & Build

1. Clone this repository:

   `git clone https://github.com/praveenbommalibits/mobile-testing-app.git`


2. Navigate to the project directory:

    `cd mobile-testing-app`


3. Build the project:

   `mvn clean install`


## Configuration

1. Configure application properties (if needed) in `application.properties`

## Deployment

1. Deploy the application to Azure or local environment
2. Creation of deploy.yml file deploy application to Azure using github Actions CI/CD pipeline.

## Running the Application

1. Start the application:
    
    `java -jar target/mobile-testing-app*.jar`


2. Access the application at http://localhost:8080.

## API Documentation

- **API Endpoint 1**: GET /api/phones
- Description: Get all phone records.
- Example: `curl http://localhost:8080/api/phones`


- **API Endpoint 2**: POST /api/phones/book/{phoneId}
- Description: Book a phone by providing the `phoneId` and `bookedBy`.
- Example: `curl -X POST "http://localhost:8080/api/phones/book/1?bookedBy=Praveen"`


- **API Endpoint 3**: POST /api/phones/return/{phoneId}
- Description: Return a phone by providing the `phoneId` `.
- Example: `curl -X POST "http://localhost:8080/api/phones/return/1"`





## UML Diagrams
Class Diagram:

https://www.plantuml.com/plantuml/uml/SyfFKj2rKt3CoKnELR1Io4ZDoSa70000

(https://cdn-0.plantuml.com/plantuml/png/LSun2W9130NGVaxnU_42MorYQI7e1R8xOGrcPc0IXKMykqPjx5v-GlYZENM_5KspaXcEbrOPZmHi8Nd0eTNbCvIMMGUSlCileJk9qYGglWwOMbEc6flmbVDk_JkFyEqzESVRcJHubi89s61XT-u6gXdsTNgcaMkESYy0)

![img_1.png](img_1.png)


Sequential Diagram:

https://www.plantuml.com/plantuml/uml/SyfFKj2rKt3CoKnELR1Io4ZDoSa70000

(https://cdn-0.plantuml.com/plantuml/png/XL9DIyGm4BttLmmzAIXTFVOWYmlYuc6BswENs8nii4v2CbjOnFzkqwOaMwfxwanv7-ynMJiMbezVRIOQrXOshKBYpFG_LQEC88QyEcZ2ZIQskctHvY0Sn7K1kqVRgGO7O9WNKAzej5EzwMa0nZNBnXHm-v3ub53jzZKKmgZ2UBuhthN-BExUw3lt4yh7KrxsubPqY9J_-4gg4unfsZXw-P2k12ymfBeQjBVo1YRDwwWpbGbVhn1Zb_2XgCTlvQGHcMcxECxvJyZD0GOoI6IXMdU6JWDNPoY9Iqv_n7IY2pN_3xXiwTdoak7iSXRvQ6bibb83VdQKyVebFDTrrQixeyaXtAzMi7j9sP6LhP6aVyI_)



![img.png](img.png)


## Reflection: 
### Most Interesting Aspect:
- **Application Architecture Design**: Delving into the intricacies of software design and crafting the application
architecture from scratch was truly fascinating. From conceptualizing the entity model to defining the interactions between different layers, it was a stimulating exercise in applying best practices and design patterns.
- **Business Logic Implementation**: Implementing the business logic for booking and returning phones provided an engaging challenge. It involved considering various scenarios, such as concurrency handling, transaction management, and input validation, which added depth to the development process.
- **Production Readiness and CI/CD Enablement**: Integrating production readiness and CI/CD enablement into the development process was particularly intriguing. Leveraging GitHub Actions for continuous integration and continuous deployment (CI/CD) and deploying the application to Azure showcased a commitment to industry best practices and demonstrated proficiency in modern software development workflows.
- **End-to-End Application Development**: Being involved in the entire lifecycle of application development, from inception to deployment, was incredibly rewarding. It allowed for a holistic understanding of the project requirements, fostering a sense of ownership and accountability for delivering a high-quality solution.

### Most Cumbersome Aspect:
- **Setup and Configuration Challenges**:
  Setting up the development environment, configuring dependencies, and conducting integration testing demanded meticulous attention to detail. 
  Debugging issues related to dependencies, configuration errors, or unexpected behavior in the application required patience and perseverance. 
  Integrating and testing with external services or databases, such as the H2 database, added complexity. Ensuring seamless deployment to Azure while meeting production standards was challenging but ultimately contributed to a more robust final product.

- **Data Simulation for Testing**:
  Simulating the existing mobile data by loading and enabling availability is cumbersome, not realistic, and not viable for a production setup. 
  Finding a more efficient and realistic approach to generate or mock test data would streamline the testing process and enhance the reliability of the application.









