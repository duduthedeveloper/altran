Hello!
There are some notes that I took in order to make your assessment easier.

IMPORTANT

You need to have docker, java 8 and maven installed in order to run the service

TO INSTALL (generating REST API, running tests and creating docker image) -
mvn clean package docker:build

TO RUN
sudo docker images -a
sudo docker run -p 8081:8081 altran

sudo* command if the OS itself requires being a root user

THE URI to access the service is /ajuntament/search?v=1

ARCHITECTURE EXPLANATION

1. I have organized the code as hexagonal (ddd) architecure - following naming convention for the layers, by implementing port/adapter
2. I decided to use @Autowired, even not being required anymore (spring 5)
3. I used design patterns(decorator, factory) and solid concepts (single responsibility, open closed, interface segregation, dependency inversion principle - I did not use liskov) and Service Design Pattern (Response Mapper - ajuntmentresponsemapper)
4. Please, check the /actuator/* in order to visualize all the metrics related to the running service
5. To test de webflux API I decided to use the WebTestClient API, part of the reactor webflux package 
6. I decided to generate API documentation directly from the code, in order to the client check what are
the possible server errors and besides that check the query parameters possible to input as filter to the
external call
7. the v=1 represents the API version
8. I decided not to add pagination, such as PageSupport or anything else because it will be controlled by start and rows on the external service. Just to be clear that I thought it not being useful, just would be overengineering

