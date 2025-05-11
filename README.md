

This code base is to simulate APIs for a telecom operator. 

<u> About code base: </u>
    
    Code skeleton with dependencies is generated from spring.io initializr. 
    It is a gradle build project which runs on springboot version 3.5.
    Needs java17 configured both on project structure and gradle build tool settings.
    Build:
    build command: ./gradlew clean build 
    Run:
    run the app: Run au.com.telecom.telecomapp.TelecomOperatorApplication class 
                (No prog args expected ! by default app pick up 8080 port
                if port is updated below endpoints' port will also need to be updated)

If app is up and running successfully, Actuator endpoints should return UP status !
http://localhost:8080/actuator/health

API spec can be rendered(Thanks to openAPI starter) at: http://localhost:8080/swagger-ui/index.html#/