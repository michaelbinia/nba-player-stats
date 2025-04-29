# NBA Player Statistics API

This API provides a set of RESTful endpoints for accessing and managing NBA player statistics. Built with Spring WebFlux, it leverages reactive programming principles for enhanced performance and scalability. The system is designed for real-time data logging, aggregation, and retrieval, focusing on scalability, high availability, and fault tolerance.

## Features

* **Real-time Data:**  Logged statistics are immediately available for aggregation and retrieval.
* **Scalable and Highly Available:** Designed for high throughput and continuous operation.
* **Reactive Architecture:** Uses Spring WebFlux for non-blocking, asynchronous processing.
* **Data Validation:**  Input data is validated to ensure data integrity.
* **Comprehensive API:** Provides endpoints for managing players, teams, and statistics.
* **Easy Deployment:**  Containerized with Docker for simplified deployment and scalability.
* **Monitoring:** Integrated with Spring Boot Actuator for health checks and metrics.


## Getting Started

### Prerequisites

* Docker
* Java 17
* Maven


### Running the Application

1. Build the project with Maven:
   ```bash
   mvn clean install
   ```

2. Build the Docker image:
   ```bash
   docker build -t nba-stats-app:latest .
   ```

3. Run the Docker container:
   ```bash
   docker run -p 8080:8080 nba-stats-app:latest
   ```

### **Build and Run using docker-compose:**
   ```bash
   docker-compose up -d --build
   ```
   This command builds the Docker image (if necessary) and starts the application in detached mode (running in the background).


### Populating with sample data (Optional)

A Python script (`/script/setup.py`) is provided to populate the database with sample data. Run it after the application is running:
```bash
python /script/setup.py  # Make sure the API is accessible at http://localhost:8080
```

## API Documentation

Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Endpoints

### Players

* **GET /api/v1/players**
    Retrieves all NBA players.
    ```json
    [
        {"id": "1", "name": "LeBron James"},
        {"id": "2", "name": "Stephen Curry"}
    ]
    ```

### Teams

* **GET /api/v1/teams**
    Retrieves all NBA teams.


### Statistics

* **POST /api/v1/statistics/player/stats** _(Planned)_
    Submits individual player game statistics.  (Request body details to be documented). Data points: Points, Rebounds, Assists, Steals, Blocks, Fouls (integer, max 6), Turnovers, Minutes Played (float, 0.0-48.0).  Team association is required.
  **Request Body Example:**
    ```json
    {
      "playerId": "123",  
      "teamId": "456",
      "gameId": "789",
      "season": "2023-2024",
      "date": "2024-03-15",
      "points": 25,
      "rebounds": 12,
      "assists": 7,
      "steals": 2,
      "blocks": 1,
      "fouls": 3, 
      "turnovers": 4,
      "minutesPlayed": 38.5
    }
    ```


* **GET /api/v1/statistics/players/{playerId}/seasons/{season}**
    Retrieves player statistics for a given season.

* **GET /api/v1/statistics/teams/{teamId}/seasons/{season}**
    Retrieves team statistics for a given season (average of player stats).

* **GET /api/v1/statistics/teams/stats**
  Retrieves statistics for all teams.

* **GET /api/v1/statistics/players/season-stats**
  Retrieves season statistics for all players.

### Actuator
* **http://localhost:8080/actuator**



## Technical Details

* **Language:** Java 17 (without ORM)
* **Data Storage:** 
* **Architecture:** The application follows a microservices architecture, with Spring WebFlux at its core.  It leverages reactive programming principles for non-blocking, asynchronous request handling.
* **Deployment:** Docker, Docker Compose/Minikube
* **Code Style:** Checkstyle is used to enforce coding standards.


## Authentication and Authorization

Currently not implemented.


## Usage Examples

Retrieve all players:

```bash
curl http://localhost:8080/api/v1/players
```