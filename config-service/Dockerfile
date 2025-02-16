# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY gradlew ./
COPY gradle gradle/
COPY build.gradle settings.gradle ./

# Copy the source code of the config-service
COPY config-service/src src/

# Grant execute permission for Gradle
RUN chmod +x gradlew

# Expose the port the application runs on
EXPOSE 8082

# Run the application
COPY config-service/build/libs/config-service-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "/app/app.jar"]
