# Fiducial Name-API

A simple Rest API to manage names.

This project uses:
- Spring Boot 3
- MySQL

If you don't have MySQL installed you can run `make up` to start the API and a Docker container for testing.

List of endpoints available:

- GET http://localhost:8080/name-api/names
  - Parameters:
    - Query:
      - page: integer (optional)
      - size: integer (optional)

- GET http://localhost:8080/name-api/{id}
  - Parameters:
   - Path:
    - id: integer (required)

- GET http://localhost:8080/name-api/names/exists/{name}
  - Parameters:
    - Path:
      - name: string (required)

- POST http://localhost:8080/name-api/names
  - Body:
    - list of strings
    - Example:
      ```
      [
        "test 1",
        "test 2",
        "test 3",
        "test 4"
      ]
      ```
