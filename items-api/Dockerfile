FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven descriptor and source
COPY pom.xml .
COPY src ./src

# Build the application JAR
RUN mvn clean package -DskipTests

# Runtime image with JRE only
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/items-api-1.0.0.jar app.jar

# Render expects services to listen on this port
ENV PORT=10000
EXPOSE 10000

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

