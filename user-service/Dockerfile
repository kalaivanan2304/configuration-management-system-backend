# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY gradlew ./
COPY gradle gradle/
COPY build.gradle settings.gradle ./

# Copy the source code of the user-service
COPY user-service/src src/

# Grant execute permission for Gradle
RUN chmod +x gradlew


# Expose the port the application runs on
EXPOSE 8081

# Run the application
COPY user-service/build/libs/user-service-0.0.1-SNAPSHOT.jar app1.jar
CMD ["java", "-jar", "/app/app1.jar"]
