# 使用 OpenJDK 21 作為基礎映像
FROM openjdk:21-jdk-slim

# 設定工作目錄
WORKDIR /app

# 複製 Maven 打包後的 JAR 檔案，並命名為 app.jar
COPY target/shopping-cart-1.0-SNAPSHOT.jar app.jar

# 暴露 8080 端口
EXPOSE 8080

# 啟動 Spring Boot 應用
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
