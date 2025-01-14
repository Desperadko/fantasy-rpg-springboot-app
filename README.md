# Fantasy RPG Application - University Project

## Project Overview
This project is a Spring Boot application meant to represent the backend portion for a Fantasy RPG game.
It runs in a Dockerized environment composed along with a MySQL database for storage.

Main project functionality:
Management, via CRUD operations, for Players, Quests, Items, etc. - each having either simple or complex relations to each other.

## Prerequisites

1. Docker
- Install [Docker](https://hub.docker.com/)

2. Docker Compose
- Comes pre-installed with Docker Dekstop on Windows/Mac.
- On Linux, install it separately: [Docker Compose Installation Guide](https://docs.docker.com/compose/install/linux/).

3. JDK (Java Development Kit)
- Version 17 ot higher is recomended.

4. Maven (for building without Docker)
- Install [Maven](https://maven.apache.org/download.cgi).

## Dependencies

1. Spring Boot Starter Web (v3.3.4)
2. Spring Boot Starter Data JPA (v3.3.4)
3. Spring Boot Starter Test (v3.3.4)
4. MySQL Connector/J (v8.2.0)
5. SpringDoc OpenAPI (v2.1.0)
6. Lombok (v1.18.30)
7. MapStruct (v1.5.5.Final)
8. H2 Database (v2.2.220)

## Installation

1. Clone the Repository
```bash
git clone https://github.com/Desperadko/fantasy-rpg-springboot-app.git
cd fantasy-rpg-springboot-app
```

2. Build and Run with Docker Compose
```bash
# Build Docker images and start containers
docker-compose up --build
```

3. Run Application Locally (Optional - without Docker)
```bash
# Build the applicaiton
mvn clean install

# Run the application
mvn spring-boot:run
```

## Application usage

After Docker containers have starter both the application and a MySQL server would be running on ports 8080 and 33060 respectfully.<br>

For Swagger UI access use the following link: [Swagger FantasyRPGApplication](http://localhost:8080/swagger-ui/index.html#/)
There you can freely test our prepared HTTP requests for each entity.<br><br>
You can:
- Get single or multiple of enitites;
- Create and Delete entities;
- Update single or multiple of entity fields;
- Connect 2 or more entities with each other.
