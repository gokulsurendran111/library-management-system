# Use Eclipse Temurin JDK 21 as base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy the JAR file into the container
COPY target/book-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your service runs on
EXPOSE 8082

# Command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
