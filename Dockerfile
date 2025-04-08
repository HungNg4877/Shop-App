# ----- STEP 1: Build ứng dụng bằng Maven -----
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# Chỉ copy những file cần thiết để cache build nhanh hơn
COPY pom.xml .
COPY src ./src

# Dùng Maven để build ứng dụng (dạng fat jar)
RUN mvn clean package -DskipTests

# ----- STEP 2: Tạo image chạy app -----
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy file jar từ image build sang image runtime
COPY --from=build /app/target/*.jar app.jar

# Mở cổng chạy app (cổng app đã cấu hình trong docker-compose là 8085)
EXPOSE 8085

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]



