# Use a base image containing Java runtime
FROM openjdk:11-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file to the container
COPY target/*.jar /app/app.jar

# Expose the port that the application will run on
EXPOSE 10000

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
