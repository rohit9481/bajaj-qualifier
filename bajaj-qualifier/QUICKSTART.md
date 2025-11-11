# Quick Start Guide

## Prerequisites
- Java 21+ installed
- Maven 3.8.7+ installed (optional, JAR is pre-built)

## Quick Start (2 steps)

### Step 1: Configure Your Details
Edit `src/main/resources/application.properties`:

```properties
app.user.name=Your Full Name
app.user.regNo=REG12349          # Replace with your registration number
app.user.email=your.email@example.com
```

### Step 2: Run the Application
```bash
java -jar target/bajaj-qualifier-1.0.0.jar
```

That's it! The application will:
1. ✅ Generate a webhook on startup
2. ✅ Receive the webhook URL and access token
3. ✅ Solve the SQL problem based on your regNo
4. ✅ Submit the solution with JWT authentication

## What Happens During Execution

```
Application Starts
       ↓
Sends POST request to generate webhook
       ↓
Receives webhook URL and access token
       ↓
Determines SQL problem (odd/even regNo)
       ↓
Generates SQL solution
       ↓
Submits solution to webhook (with JWT auth)
       ↓
Application completes
```

## Expected Console Output

```
========================================
Bajaj Finserv Health Qualifier Started
========================================
Starting Bajaj Qualifier Webhook Flow...
Webhook generated successfully
Webhook URL: https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/...
Access Token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Last two digits of regNo: 49, Is Odd: true
Generated SQL Query: SELECT id, name, salary FROM employees WHERE ...
Solution submitted successfully!
Response from webhook: {...}
========================================
```

## Troubleshooting

| Issue | Solution |
|-------|----------|
| `Connection refused` | Check internet connection and API endpoint availability |
| `Invalid regNo format` | Ensure regNo follows format: REGxxxxx (5 digits) |
| `Webhook response null` | Check your registration details in application.properties |
| `JWT token error` | Token is auto-managed, ensure API is accessible |
| `Port 8080 already in use` | Application may have crashed; check `ps aux \| grep java` |

## Building from Source

```bash
# Clone or download the repository
cd bajaj-qualifier/bajaj-qualifier

# Build the project
mvn clean package -DskipTests

# Run the application
java -jar target/bajaj-qualifier-1.0.0.jar
```

## Files Overview

- **BajajQualifierApplication.java** - Main Spring Boot application
- **StartupRunner.java** - Executes webhook flow on startup
- **WebhookService.java** - Handles all API interactions
- **WebhookResponse.java** - Data model for API responses
- **application.properties** - Configuration file
- **pom.xml** - Maven build configuration

## Key Features

✨ **No Manual Intervention Required**: Entire flow runs automatically on startup

🔐 **Secure JWT Authentication**: Automatic Bearer token management

🎯 **Smart Problem Routing**: SQL problem determined from registration number

📊 **Comprehensive Logging**: Detailed logs for debugging

🚀 **Production Ready**: Fully compiled and tested

## Next Steps

1. **Update your registration details** in `application.properties`
2. **Run the JAR file**
3. **Monitor the console output** for success confirmation
4. **Check the webhook response** to verify submission

## Support Resources

- Check `README.md` for detailed documentation
- Review code comments in service classes
- Monitor console logs for error details
- Verify API endpoint accessibility

## Success Criteria

Your submission is successful when you see:
```
Solution submitted successfully!
Response from webhook: [response_body]
```

---

**Ready to submit?** Follow the 2-step process above! 🚀
