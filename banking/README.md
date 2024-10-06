# AccountDetails API

## Frameworks Used

Java 17, Spring Boot 3, Maven 3.8.
For removing bolier plate code --- Lombok, --Mapstruct.
API Documentation - OpenApi 3.0.3.
Container- Docker
Testing - Junit jupiter, Mockito, MockMvc
No checkstyles files used

## Considerations and assumptions
1. Since the requirement was not to persist in any external DB, the developer chose to use hazelcast over In memory H2 DB
2. API first approach was chosen 
3. Due to lack of time the developer could configure spring cloud config server to externalize the configuration properties
4. No plugins used to generate any class or methods
5. Currency is not taken into consideration to keep the application simple
6. Transaction API is not exposed to outside hence entity is accepted as input

## Code setup and run(if docker is installed)
1. Clone the project from gitHub
2. Navigate to project root folder and execute 
       docker compose build   and then docker compose up
3. This stage will execute all 3 APIs as a seperate image in a container

## Code setup and run(if docker is NOT installed)
1. Clone the project from gitHub
2. Navigate to project root folder and execute mvn clean install on the project root directory
3. Navigate to <cloned path>\banking\service-registry\target, execute java -jar <module>-<version>.jar
4. Navigate to <cloned path>\banking\accounts\target, execute java -jar <module>-<version>.jar
5. Navigate to <cloned path>\banking\transactions\target, execute java -jar <module>-<version>.jar
6. UserName and password to access the respective API are defined in the application.properties as security is enabled

## API End points to test
Service registry- http://localhost:8761/login
Open New Account- http://localhost:8082/api/v1/accounts/open/1?initialCredit=50
Get customerAccountDetails- http://localhost:8082/api/v1/accounts/customer/1


## Packaging structure

Considering microservice architecture functional packaging is considered so that it can be deployed as seperate artifacts

## Production ready considerations:

Spring boot application runs with active profiles hence properties per environment is placed and the prod profile is configured
1. Spring security is enabled with different credentials for prod with roles for differnt endpoint. Roles can also be configured in tables and can be made run time rather than hardcoding
2. Actuator endpoints are made available to capture health and metrics 
3. Passwords are hardcoded in the code or property file as there is no vault integration done and in real time it can be fetched from secure vaults
