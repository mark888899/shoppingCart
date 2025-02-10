# SideProject購物車後端

## 簡介
本專案是基於 Spring Boot 的後端服務，提供簡易電商平台 API，包括用戶管理、商品管理、購物車、訂單處理等功能。

## 技術
- **後端**：Spring Boot
- **資料庫**：MySQL
- **ORM**：Spring Data JPA、
- **身份驗證與授權**：JWT（JJWT）
- **密碼加密**：BCrypt
- **構建工具**：Maven
- **API文件**：Springdoc OpenAPI
- **JSON處理**：Jackson（因應JJWT與Spring starter衝突）
- **部署**：Docker、Kubernetes
- **測試**：JUnit 5（Jupiter）、Mockito

## 環境設定
### 1. 需求
- **Java 21**
- **Maven 3+**
- **MySQL 8+**
- **Docker（可選）**

### 2. 設定 MySQL 資料庫
確保 MySQL 服務運行，並建立專案所需的資料庫：
```sql
CREATE DATABASE shopping_cart;
```
其他相關資料庫相關資料請查閱 `Schema.txt` 與 `mockDBSchema`

### 3. 設定 application.properties
修改 `src/main/resources/application.properties`，設定資料庫連線：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopping_cart
spring.datasource.username=root
spring.datasource.password=your_password
```

## 專案啟動
### 1. 使用 Maven 直接啟動
```sh
mvn spring-boot:run
```

### 2. 使用 Docker 部署
建置 Docker 映像檔：
```sh
docker build -t shopping-cart-backend .
```
啟動容器：
```sh
docker run -p 8080:8080 shopping-cart-backend
```

### 3. 使用 Kubernetes 部署
應用 MySQL 部署：
```sh
kubectl apply -f mysql-deployment.yaml
```
應用後端服務：
```sh
kubectl apply -f shoppingcart-deployment.yaml
```

## API 端點
| 方法 | 端點 | 描述 |
|------|------|------|
| POST | `/api/users/register` | 用戶註冊 |
| POST | `/api/users/login` | 用戶登入 |
| GET  | `/api/products` | 獲取商品列表 |
| POST | `/api/cart/add` | 加入購物車 |
| GET  | `/api/orders` | 查詢訂單 |

完整 API 文件請參考 `src/main/resources/static/api-docs`。

## 測試
執行所有測試：
```sh
mvn test
```

## 貢獻
歡迎 PR！請遵循專案的程式風格和提交規範。
