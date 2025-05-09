# Sử dụng base image Java 21 JDK để build ứng dụng
FROM eclipse-temurin:21-jdk AS build

# Thư mục chứa ứng dụng trong container
WORKDIR /app

# Copy file JAR đã được build sẵn vào container
COPY target/*.jar app.jar

# Sử dụng Eclipse Temurin JRE để chạy ứng dụng
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/app.jar app.jar

# Cấu hình port mặc định (tùy service)
EXPOSE 8079

# Command để chạy ứng dụng JAR bằng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
