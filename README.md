# Code Challenge: Authorizer

### Technical and architectural decisions

 - separated app into different layers
 - there should be an easy way to add more to validator in order to handle more cases, currently in only one class for sake of simplicity.

### Reasoning about the frameworks used 

 - no need at this point for dependency injection so no spring
 - used lombok to keep boiler plate at minimum
 - jackson for json parsing

### How to compile and run the project

#### compile:

mvn clean install

#### run:

java -jar target/authorizer-1.0-SNAPSHOT-jar-with-dependencies.jar < examples/multiple-logic-violation

#### requirements:

 - Apache Maven 3.5.4
 - Java version: 11 or greater. 

### Additional notes