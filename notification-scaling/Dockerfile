# Use an official OpenJDK 21 runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file (replace with your actual jar name)
COPY build/libs/notification-scaling-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["taskset", "-c", "0-4", "java", "-Xms15g", "-Xmx16g", "-jar", "app.jar"]
