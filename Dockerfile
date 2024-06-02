# Use Ubuntu as the base image for the first stage
FROM ubuntu:latest AS build

# Install necessary dependencies
RUN apt-get update && apt-get install -y wget unzip openjdk-17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install Gradle (assuming you have a Gradle project)
RUN wget https://services.gradle.org/distributions/gradle-6.5-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-*.zip \
    && rm /tmp/gradle-*.zip
ENV GRADLE_HOME=/opt/gradle/gradle-6.5
ENV PATH=${GRADLE_HOME}/bin:${PATH}

# Run gradlew to build the project
RUN ./gradlew clean build

# Use the official Selenium Grid Standalone with Chrome image as the base for the second stage
FROM selenium/standalone-chrome:latest

# Set the working directory in the container
WORKDIR /app

# Copy the built artifacts from the first stage
COPY --from=build /app/build/libs/*.jar /app/

# Copy the Gradle wrapper from the first stage
COPY --from=build /app/gradlew /app/
COPY --from=build /app/gradle /app/gradle

# Make the Gradle wrapper executable
# Run gradlew to build the project with verbose output
RUN ./gradlew clean test --info

# ...

# Command to run the tests
CMD ["./gradlew", "clean", "test"]