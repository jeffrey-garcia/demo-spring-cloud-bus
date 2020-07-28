- run the app in single instance (port 8080)
    ```sh
    ./mvnw clean spring-boot:run -Dspring-boot.run.profiles=local
    ```
- run the app with multiple instances (port 8081 and port 8082)
    ```sh
    ./mvnw clean
    ./mvnw spring-boot:run -Dspring-boot.run.profiles=local,app1
    ./mvnw spring-boot:run -Dspring-boot.run.profiles=local,app2
    ```