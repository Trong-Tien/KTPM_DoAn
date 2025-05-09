# Sử dụng base image Java 21 JDK để build
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Sử dụng JRE để chạy
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]