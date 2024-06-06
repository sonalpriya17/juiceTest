#!/bin/bash

# Run the Gradle clean and test tasks
./gradlew clean test

# Echo the location of the test report
echo "Test report available at: build/reports/tests/test/index.html"

# Optionally, you can add a command to open the test report in a browser on MacOS
open build/reports/tests/test/index.html
