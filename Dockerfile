# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

RUN mkdir -p /usr/local/newrelic
ADD ./newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
ADD ./newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml


# Copy the jar file created after Maven build
COPY target/ServiceA-1.0-SNAPSHOT.jar /app/myapp.jar

# Unzip the New Relic agent downloaded during the build
#RUN mkdir newrelic
#COPY target/newrelic/* /app/newrelic/
#
## Environment variables (Edit as required)
#ENV license_key="Rookie-ServiceA"
#ENV app_name="5278f4e55271fb999b6aeed083d94c83FFFFNRAL"

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
#ENTRYPOINT ["java", "-javaagent:/app/newrelic/newrelic.jar", "-Dnewrelic.config.file=/app/newrelic/newrelic.yml", "-jar", "/app/myapp.jar"]

ENTRYPOINT ["java","-javaagent:/usr/local/newrelic/newrelic.jar","-jar","/app/myapp.jar"]

