#!/bin/bash

# This script automates the process of cleaning and building all
# the Maven microservice projects. It's designed to be run from the
# root directory of your project (where the service folders are located).

# Exit immediately if any command fails. This ensures that if one build fails,
# the script stops and doesn't proceed with outdated artifacts.
set -e

echo "Starting the build process for all microservices..."
echo "---------------------------------------------------"

# Build discovery-server first, as it is often a dependency for others.
echo "-> Building discovery-server..."
cd discovery-server
./mvnw clean package -DskipTests
echo "✅ discovery-server build complete."
cd ..

# Build api-gateway
echo "-> Building api-gateway..."
cd api-gateway
./mvnw clean package -DskipTests
echo "✅ api-gateway build complete."
cd ..

# Build user-service
echo "-> Building borrow-service..."
cd user-service
./mvnw clean package -DskipTests
echo "✅ user-service build complete."
cd ..

# Build book-service
echo "-> Building book-service..."
cd book-service
./mvnw clean package -DskipTests
echo "✅ book-service build complete."
cd ..

# Build borrow-service
echo "-> Building borrow-service..."
cd borrow-service
./mvnw clean package -DskipTests
echo "✅ borrow-service build complete."
cd ..

echo "---------------------------------------------------"
echo "🎉 All microservices have been built successfully!"
echo "You can now run 'docker-compose up --build' to start your application."