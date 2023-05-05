# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file to the working directory
COPY build/libs/*.jar /app/app.jar

# Expose the default Spring Boot application port
EXPOSE 8080

# Set the entrypoint for the final image
ENTRYPOINT ["java", "-jar", "app.jar"]