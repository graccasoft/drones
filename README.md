# Drones
Simple drone dispatch service:

## Technologies
* Spring Boot 3.0
* Maven
 
## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+


To build and run the project, follow these steps:

* Clone the repository: `https://github.com/graccasoft/drones.git`
* Navigate to the project directory: cd drones
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run 
* Run tests:  mvn -Dtest=DispatchServiceImplTest test

-> The application will be available at http://localhost:8080.

-> For Restful API documentation browse http://localhost:8080/swagger-ui/index.html