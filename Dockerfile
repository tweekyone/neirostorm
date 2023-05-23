# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file to the working directory
COPY build/libs/*.jar ./
COPY config/application.properties ./

# Set the entrypoint for the final image
ENTRYPOINT ["java", "-jar", "neirostorm-0.0.1-SNAPSHOT.jar", "--spring.config.location=application.properties"]
