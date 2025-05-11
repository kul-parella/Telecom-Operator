

This code base is to simulate APIs for a telecom operator. 

<u> About code base: </u>
    
    Code skeleton with dependencies is generated from spring.io initializr. 
    It is a gradle build project which runs on springboot version 3.4.5.
    Needs java17 configured both on project structure and gradle build tool settings.
    Build:
    build command: ./gradlew clean build 
    Run:
    run the app: Run au.com.telecom.app.TelecomOperatorApplication class 
                (No prog args expected ! by default app pick up 8080 port
                if port is updated below endpoints' port will also need to be updated)

If app is up and running successfully, Actuator endpoints should return UP status !
http://localhost:8080/actuator/health

API spec can be rendered(Thanks to openAPI starter) at: http://localhost:8080/swagger-ui/index.html#/

App will create in memory database(h2) and load the data from data.sql into 2 newly created tables 
1. customers
2. phone_numbers 
schemas and load of data can be access in resources/data.sql and schema.sql 

DB can be accessed at - http://localhost:8080/h2-console

Testcases are covering 98% of code (excluded interfaces and App)
jdbc url - jdbc:h2:mem:telecomdb 
other configurations are available in app properties file 

In service layer, using Post construct loading the static data structure to load phoneNumbers while initializing ! 
and only loading 5 records as initial load same as pagination size. 