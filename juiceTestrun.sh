#!/bin/bash

# Run the Gradle clean and test tasks
./gradlew clean test

# Optionally, you can add a command to open the test report in a browser on MacOS
# open build/reports/tests/test/index.html