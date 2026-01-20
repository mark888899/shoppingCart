# 使用 OpenJDK 21 作為基礎映像
FROM maven:3.9.6-eclipse-temurin-21 AS build

# 設定工作目錄
WORKDIR /app

# 複製 Maven POM & 程式碼
COPY pom.xml .
COPY src/ src/

# 下載依賴 & 打包 JAR
RUN mvn clean package -DskipTests

# 使用輕量化 JDK 運行 Spring Boot
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# 複製已打包的 JAR
COPY --from=build /app/target/shopping-cart-1.0-SNAPSHOT.jar app.jar

# 暴露 8080 端口
EXPOSE 8080

# 啟動 Spring Boot 應用
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
