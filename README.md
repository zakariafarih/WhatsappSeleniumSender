
# WhatsApp Sender Selenium


## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Security](#security)
- [Improvements & Suggestions](#improvements--suggestions)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

**WhatsApp Sender Selenium** is a Spring Boot application that leverages Selenium WebDriver to automate the sending of WhatsApp messages. It provides a RESTful API to send messages to individual phone numbers or groups, making it ideal for automating notifications, alerts, or bulk messaging tasks.

## Features

- **Automated Messaging**: Send WhatsApp messages programmatically to individuals or groups.
- **RESTful API**: Easily integrate with other services using the provided API endpoints.
- **Secure Authentication**: Implements Spring Security with role-based access control.
- **Configurable WebDriver**: Customize Selenium WebDriver settings for different environments.
- **Swagger Integration**: Interactive API documentation for easy testing and integration.

## Technologies Used

- **Java 23**
- **Spring Boot**
- **Spring Security**
- **Selenium WebDriver**
- **ChromeDriver**
- **WebDriverManager**
- **Swagger (OpenAPI)**
- **Lombok**
- **Maven**

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 17 or higher**: [Download Here](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- **Maven**: [Download Here](https://maven.apache.org/download.cgi)
- **Google Chrome Browser**: Ensure you have the latest version installed.
- **Git**: [Download Here](https://git-scm.com/downloads)

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-username/WhatsappSenderSelenium.git
   cd WhatsappSenderSelenium
   ```

2. **Build the Project**

   Use Maven to build the project:

   ```bash
   mvn clean install
   ```

3. **Run the Application**

   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`.

### Configuration

The application uses properties defined in `application.properties` or `application.yml`. Below are the key configurations:

```properties
# Selenium WebDriver configurations
selenium.headless=true
selenium.user-data-dir=./user-data
selenium.profile-directory=Default

# Spring Security configurations
spring.security.user.name=admin
spring.security.user.password=secret

# Server configurations
server.port=8080
```

**Environment Variables:**

For enhanced security and flexibility, it's recommended to externalize configurations using environment variables or a dedicated configuration server.

## Usage

### Authentication

The API is secured using HTTP Basic Authentication. Use the following credentials:

- **Username**: `admin`
- **Password**: `secret`

> **Note**: These credentials are defined in `SecurityConfig.java` using an in-memory user. For production, consider using a persistent user store.

### Sending a Message

To send a WhatsApp message, make a `POST` request to `/api/messages/send` with the following JSON payload:

```json
{
  "phoneNumber": "1234567890", // Recipient's phone number or group ID
  "message": "Hello, this is a test message!",
  "isGroup": false // Set to true if sending to a group
}
```

**Example using `curl`:**

```bash
curl -X POST http://localhost:8080/api/messages/send   -H "Content-Type: application/json"   -u admin:secret   -d '{
        "phoneNumber": "1234567890",
        "message": "Hello, this is a test message!",
        "isGroup": false
      }'
```

### Swagger API Documentation

Access the interactive API documentation at: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Documentation

### `POST /api/messages/send`

**Description**: Sends a WhatsApp message to a specified phone number or group.

**Request Body**:

```json
{
  "phoneNumber": "string", // Required
  "message": "string",     // Required
  "isGroup": "boolean"     // Optional, default: false
}
```

**Responses**:

- **200 OK**: Message sent successfully.
- **400 Bad Request**: Missing or invalid request parameters.
- **401 Unauthorized**: Authentication failed.
- **500 Internal Server Error**: An error occurred while sending the message.

## Security

The application uses Spring Security to protect API endpoints. Key security configurations include:

- **HTTP Basic Authentication**: Simple authentication mechanism using username and password.
- **Role-Based Access Control**:
  - **ADMIN**: Full access, including administrative endpoints.
  - **USER**: Limited access to send messages.

**Security Configuration Highlights**:

- **In-Memory User Store**: Defined in `SecurityConfig.java` for simplicity. Replace with a persistent user store (e.g., JDBC, LDAP) for production environments.
- **Password Encoding**: Uses BCrypt for secure password hashing.
- **CSRF Protection**: Disabled for simplicity. **_It's recommended to enable CSRF protection in production._**

## Improvements & Suggestions

To enhance the functionality, security, and scalability of the **WhatsApp Sender Selenium** application, consider implementing the following improvements:

1. **Persistent User Store**:
   - Replace the in-memory user details manager with a persistent store like a database or LDAP for managing users.

2. **Enable CSRF Protection**:
   - Re-enable CSRF protection to safeguard against cross-site request forgery attacks, especially for state-changing operations; ( It was disabled because i didn't have time to make authentication possible while csrf was enabled ) .

3. **Enhanced Exception Handling**:
   - Implement a global exception handler using `@ControllerAdvice` to manage and format error responses consistently.

4. **Logging and Monitoring**:
   - Integrate a logging framework (e.g., Logback, Log4j2) for better monitoring and debugging.
   - Consider adding metrics and health checks using Spring Actuator.

5. **Configuration Management**:
   - Externalize configurations using environment variables or a configuration server like Spring Cloud Config.
   - Secure sensitive information (e.g., passwords) using secrets management tools.

6. **Unit and Integration Testing**:
   - Add comprehensive unit tests for service and controller layers.
   - Implement integration tests to ensure end-to-end functionality.

7. **Dockerization**:
   - Containerize the application using Docker for easier deployment and scalability.
   - Manage Selenium WebDriver in Docker containers to ensure consistency across environments.

8. **Support for Multiple Browsers**:
   - Make the choice of WebDriver (e.g., Firefox, Edge) configurable to support different browsers.

9. **Scalability Enhancements**:
   - Implement a WebDriver pool to handle multiple concurrent message sending operations.
   - Use asynchronous processing (e.g., `@Async`, message queues) to improve performance.

10. **Security Enhancements**:
    - Implement JWT-based authentication for better security and scalability.
    - Add rate limiting to prevent abuse of the API endpoints.

11. **Robust Selenium Interactions**:
    - Enhance the Selenium scripts to handle dynamic changes in WhatsApp Web's UI.
    - Implement retry mechanisms for transient failures.

12. **User Interface**:
    - Develop a frontend interface for users to interact with the API without needing to use tools like `curl` or Postman.

13. **Documentation**:
    - Expand the README with more detailed examples, troubleshooting tips, and contribution guidelines.
    - Generate API client SDKs for easier integration with other applications.

## Contributing

Contributions are welcome! Please follow these steps to contribute:

1. **Fork the Repository**

2. **Create a Feature Branch**

   ```bash
   git checkout -b feature/YourFeature
   ```

3. **Commit Your Changes**

   ```bash
   git commit -m "Add some feature"
   ```

4. **Push to the Branch**

   ```bash
   git push origin feature/YourFeature
   ```

5. **Open a Pull Request**

Please ensure that your code follows the project's coding standards and that all tests pass.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

For any inquiries or support, please contact [zakariafarih142@gmail.com](mailto:zakariafarih142@gmail.com).

---

*Happy Coding! 🚀*
