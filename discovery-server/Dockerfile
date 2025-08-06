# Use a JDK base image
FROM eclipse-temurin:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file to the container
COPY target/discovery-server-0.0.1-SNAPSHOT.jar app.jar

# Expose the port Eureka runs on
EXPOSE 8761

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
