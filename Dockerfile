# Use Eclipse Temurin as the base image
FROM eclipse-temurin:25-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file with specific name
COPY target/ToDo_APP-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prd

# Expose the port the app runs on
EXPOSE 9191

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Make a JAR file before running this command in your terminal.
# Ensure docker is running, preferably in WSL.

# docker-compose up --build -d
