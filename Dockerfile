# Use an official OpenJDK runtime as a parent image
FROM openjdk:11

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install Gradle
RUN wget https://services.gradle.org/distributions/gradle-6.5-bin.zip -P /tmp
RUN unzip -d /opt/gradle /tmp/gradle-*.zip
ENV GRADLE_HOME=/opt/gradle/gradle-6.5
ENV PATH=${GRADLE_HOME}/bin:${PATH}

# Run gradlew to ensure it's executable and cache dependencies
RUN ./gradlew --version


# Command to run the test
CMD ["./gradlew", "clean", "test"]