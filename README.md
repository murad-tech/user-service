# User Service
> User Service API with Authentication and Authorization included.  
> Suitable as microservice for domains like ecommerce where not as much user information needed, 
> but anytime we can increase complexity 

### Stack/Dependencies:
- Java 21
- Spring Boot 3
  - Spring Data JPA
  - Spring Security
- Postgres (Dockerized)
- Project Lombok

## Project Setup
[//]: # (TODO: add shell script or make file to set up hassle free framework  )

### Setup database with Docker
Below command will run docker compose which will create docker container based on `.dev.env` file
> **_NOTE:_** actual production will be changed to `.env` file which is ignored here.  
> If you want to change container config just update `.dev.env` no need to update `application.yml`  
> This is dynamic programming 😎

```shell
docker compose --env-file .dev.env up -d
# or if you use .env file
docker compose up -d
```

To see if database created execute below commands
```shell
docker ps
docker exec -it [CONTAINER_ID] sh
psql -U postgres -d users
```

Run Spring Boot application on a separate tab, this should create a DB tables automatically
```shell
mvn spring-boot:run
# OR in detached mode:
mvn spring-boot:start
mvn spring-boot:stop  # command to stop app once you done
```

