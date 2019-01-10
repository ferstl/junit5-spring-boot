#junit5-spring-boot

*- Shows that running JUnit5 Tests with a package selector does not work within a Spring Boot JAR.*

### How to build and run:

    cd junit5-spring-boot
    
    mvn clean package
    java -jar junit5-spring-boot-application/target/junit5-spring-boot-application-1.0.0-SNAPSHOT.jar
    
### Observed Behaviour:
- When executed within an IDE which uses a regular classpath, the tests are executed
- When executed as Spring Boot JAR, the tests are not executed
- When executed with a class selector, the tests are executed

### Possible Cause
The `junit5-spring-boot-tests` JAR which contains the tests is located in `BOOT-INF/lib` within the `junit5-spring-boot-application` JAR.
JUnit tries to load the class by executing `Files.walkFileTree()` which seems not to work with nested JAR files.
