# Example Reactor Kafka in Kotlin
Spring WebFlux project with Reactor Kafka by create hypothesis scenario E-Learning platform

Programming Language: Kotlin

## Pre-requisites
- JDK 17+
- Docker or Podman + Podman Compose

## How to run
This is the template project, so you can use it as a base for your project.

1. Actually, you can start by running docker compose and start application
    ```shell
    cd local-kafka
    docker compose up -d
    ```
2. If you work with podman, you can use podman compose
   ```shell
   cd local-kafka
   podman-compose up -d
   ``` 
3. Run Content Delivery service (Difference terminal if needed)
    ```shell
    cd content-delivery
    ./gradlew bootRun --args='--server.port=8080'
    ```
4. Run Interaction service (Difference terminal if needed)
    ```shell
    cd interaction
    ./gradlew bootRun --args='--server.port=8081'
    ```
