# Use an official OpenJDK runtime as a base image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
#COPY build/libs/Booking-0.0.1-SNAPSHOT.jar /app/app.jar

COPY build/libs/taskmanagement-0.0.1-SNAPSHOT.jar /app/app.jar

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]
