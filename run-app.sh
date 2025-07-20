#!/bin/bash

# Clean and build the project
echo "Building Spring Boot application..."
./mvnw clean install -DskipTests || { echo "Build failed"; exit 1; }

# Run the application
echo "Starting Spring Boot application..."
java -jar target/*.jar
