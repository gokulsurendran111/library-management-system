# Use OpenJDK 21 as the base image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/notification-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (change if needed)
EXPOSE 8084

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
