## Introduction

**vhp-project** is a web application built with Java 11 using Spring Boot, Spring Security, MySQL, JWT, Swagger, Log4j, and an Exception Handler. The application provides various features such as user authentication, authorization, login, registration, and CRUD operations for managing users.

## Key Technologies

- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html): The Java programming language version 11 is used to build the application.
- [Spring Boot](https://spring.io/projects/spring-boot): Spring Boot framework is used for fast and easy Java application development.
- [Spring Security](https://spring.io/projects/spring-security): Spring Security library is used for managing user authentication and authorization.
- [MySQL](https://www.mysql.com): MySQL database is used for storing user data.
- [JWT (JSON Web Tokens)](https://jwt.io): JWT is used for secure user authentication and authorization.
- [Swagger](https://swagger.io): Swagger is used for API documentation.
- [Log4j](https://logging.apache.org/log4j/2.x): Log4j is used for logging application events.
- Exception Handler: An Exception Handler is implemented to handle and manage exceptions gracefully.

## Features

- **Authentication**: The application provides user authentication using Spring Security and JWT.
- **Authorization**: User access rights are managed based on roles and permissions using Spring Security.
- **Login**: Users can log in with their credentials and obtain a JWT token for authenticated requests.
- **Registration**: New users can register and create an account to access the application.
- **CRUD User**: The application allows creating, reading, updating, and deleting user information.

## Installation and Run

1. Make sure you have [Java 11 JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) installed on your computer.

2. Clone the repository from GitHub:

```bash
git clone https://github.com/username/repo.git
cd repo
```

3. Set up your MySQL database and update the application.properties file with the appropriate database credentials.

4. Run the application:
```bash
./mvnw spring-boot:run
```

The application will run on the default port 8080. You can access the application in your web browser at http://localhost:8080.

## API Documentation
The API endpoints are documented using Swagger. You can access the Swagger UI at http://localhost:8080/swagger-ui.html.

## Logging
Application events are logged using Log4j. The log files can be found in the logs folder.

## Exception Handling
The application includes a custom Exception Handler to handle and log exceptions gracefully.

## Contribution
If you wish to contribute to the project, please create a pull request or report issues. We welcome contributions from the community.