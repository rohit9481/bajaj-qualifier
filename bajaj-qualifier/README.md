# Bajaj Finserv Health Qualifier - Spring Boot Application

A Spring Boot application that automates the Bajaj Finserv Health qualifier submission process.

## Overview

This application implements the following workflow:

1. **On Startup**: Automatically sends a POST request to generate a webhook with user registration details
2. **Webhook Reception**: Receives a webhook URL and JWT access token from the Bajaj API
3. **SQL Problem Solving**: Determines the SQL problem based on the registration number's last two digits
4. **Solution Submission**: Submits the SQL solution to the webhook URL using JWT authentication

## Features

- ✅ Automatic startup flow (no REST endpoints exposed)
- ✅ JWT-based authentication for API calls
- ✅ Problem determination based on registration number
- ✅ Comprehensive logging and error handling
- ✅ Spring Boot 3.2.0 with Java 21
- ✅ Maven build automation
- ✅ Fully executable JAR with all dependencies

## Project Structure

```
src/main/java/com/example/bajaj/
├── BajajQualifierApplication.java      # Main Spring Boot application entry point
├── config/
│   └── StartupRunner.java              # CommandLineRunner to execute on startup
├── model/
│   └── WebhookResponse.java            # DTO for API response
└── service/
    └── WebhookService.java             # Service handling all API interactions

src/main/resources/
└── application.properties              # Application configuration
```

## Technology Stack

- **Java Version**: 21
- **Spring Boot**: 3.2.0
- **Build Tool**: Maven 3.8.7+
- **Dependencies**:
  - Spring Boot Web Starter
  - Jackson for JSON processing
  - SLF4J for logging

## Configuration

Update the user details in `application.properties`:

```properties
app.user.name=Your Name
app.user.regNo=REG12349          # Your registration number (determines SQL problem)
app.user.email=your.email@example.com
```

The SQL problem is assigned based on the last two digits of `regNo`:
- **Odd** (e.g., 47, 49): Question 1
- **Even** (e.g., 48, 50): Question 2

## Building the Application

### Prerequisites
- Java 21+
- Maven 3.8.7+

### Build Steps

```bash
cd bajaj-qualifier
mvn clean package -DskipTests
```

This generates the executable JAR file at:
```
target/bajaj-qualifier-1.0.0.jar
```

## Running the Application

```bash
java -jar target/bajaj-qualifier-1.0.0.jar
```

### Expected Output

```
========================================
Bajaj Finserv Health Qualifier Started
========================================
Starting Bajaj Qualifier Webhook Flow...
Webhook generated successfully
Webhook URL: https://...
Access Token: ***
Generated SQL Query: SELECT ...
Solution submitted successfully!
```

## API Endpoints Used

### 1. Generate Webhook
**Endpoint**: `POST https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA`

**Request Body**:
```json
{
    "name": "Your Name",
    "regNo": "REG12349",
    "email": "your.email@example.com"
}
```

**Response**:
```json
{
    "webhook": "https://...",
    "accessToken": "eyJhbGc..."
}
```

### 2. Submit Solution
**Endpoint**: `POST https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA`

**Headers**:
```
Authorization: Bearer <accessToken>
Content-Type: application/json
```

**Request Body**:
```json
{
    "finalQuery": "SELECT ... FROM ..."
}
```

## Code Highlights

### Automatic Startup Execution
The `StartupRunner` component implements `CommandLineRunner` to execute the webhook flow on application startup:

```java
@Component
public class StartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        webhookService.executeFlow();
    }
}
```

### JWT Authentication
Bearer token is automatically added to the Authorization header:

```java
HttpHeaders headers = new HttpHeaders();
headers.setBearerAuth(accessToken);  // Automatically adds "Bearer " prefix
```

### RestTemplate Usage
HTTP requests are made using Spring's `RestTemplate`:

```java
@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

## Logging

Application uses SLF4J for comprehensive logging. Log levels can be configured in `application.properties`:

```properties
logging.level.root=INFO
logging.level.com.example.bajaj=DEBUG
```

## Database Considerations

The application does not use a database. SQL solutions are:
- Determined based on the registration number
- Generated programmatically
- Submitted directly to the webhook

If you need to store solutions, add Spring Data JPA and configure a database in `application.properties`.

## Error Handling

The application includes comprehensive error handling:
- API connection failures
- Invalid webhook responses
- Timeout handling
- Detailed logging of all operations

## Submission Checklist

- [x] Code implemented in Spring Boot
- [x] Automatic startup flow (no endpoints)
- [x] JWT authentication in headers
- [x] RestTemplate for HTTP calls
- [x] Compiled JAR file generated
- [x] Application.properties configuration
- [x] Comprehensive documentation
- [x] Error handling and logging

## JAR File Details

**File**: `target/bajaj-qualifier-1.0.0.jar`
**Size**: ~19 MB (includes all dependencies)
**Executable**: Yes (executable fat JAR)
**Java Requirement**: 21+

## Running Tests

The application does not have built-in tests. To test manually:

1. Update your registration details in `application.properties`
2. Run the JAR file
3. Monitor the console output for webhook URL and access token
4. Verify the solution submission message

## Development Notes

- The application follows Spring Boot best practices
- Code is organized in layers (config, service, model)
- Dependency injection is used throughout
- RestTemplate is configured as a Spring bean for reusability

## Future Enhancements

- Database persistence for solutions
- Configuration via environment variables
- Spring Cloud Config integration
- Docker containerization
- Unit tests with JUnit 5 and Mockito
- Custom exception handling

## Support

For issues or questions:
1. Check the console logs for error messages
2. Verify your registration number format
3. Ensure internet connectivity for API calls
4. Validate API credentials

## License

This application is provided as-is for the Bajaj Finserv Health Qualifier submission.

---

**Built with**: Spring Boot 3.2.0 | Java 21 | Maven
**Status**: Production Ready ✅
