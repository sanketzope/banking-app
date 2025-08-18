## Banking App 🏦🚀

A simple Spring Boot REST API for managing bank accounts: create accounts, view, deposit, withdraw, list, and delete. Secured with HTTP Basic auth and role-based access (admin-only delete). Built with Spring Boot 3, Spring Data JPA, and MySQL.

### Tech Stack ⚙️
- **Language**: Java 17
- **Framework**: Spring Boot 3.x (Web, Data JPA, Security)
- **Database**: MySQL
- **Build**: Maven (wrapper included)
- **Utilities**: Lombok

### Prerequisites ✅
- Java 17 installed
- MySQL running locally
- Maven (optional — wrapper `mvnw`/`mvnw.cmd` provided)

### Project Layout 🗂️
- Module root: `banking-app/`
  - App entry: `banking-app/src/main/java/com/sanket/banking/BankingAppApplication.java`
  - REST controllers in `controller/`, services in `service/`, JPA entities in `entity/`

### Configuration 🔧
Edit `banking-app/src/main/resources/application.properties` to match your local MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banking_app
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

Create the database beforehand:

```sql
CREATE DATABASE banking_app;
```

### Run the App ▶️
- Windows (PowerShell/CMD):

```bash
cd banking-app
./mvnw.cmd spring-boot:run
```

- macOS/Linux:

```bash
cd banking-app
./mvnw spring-boot:run
```

App starts on `http://localhost:8080` by default.

### Security 🔐
HTTP Basic authentication is enabled for all `/api/accounts/**` endpoints.

- Users defined in-memory (see `UserConfig`):
  - `admin` / `admin123` — roles: `ADMIN`
  - `user` / `user123` — roles: `USER`
- Authorization rules (see `SecurityConfig`):
  - `DELETE /api/accounts/**` → requires role `ADMIN`
  - All other `/api/accounts/**` → requires authentication (any user)

### API Endpoints 📚

Note: The request/response JSON uses the field name `accountHoldername` (spelling matches the code).

| Method | Path                         | Body (JSON)                            | Auth        |
|-------:|-------------------------------|----------------------------------------|-------------|
| POST   | `/api/accounts`              | `{ "accountHoldername": "Alice", "balance": 1000 }` | user/admin |
| GET    | `/api/accounts/{id}`         | —                                      | user/admin  |
| PUT    | `/api/accounts/{id}/deposit` | `{ "amount": 250 }`                  | user/admin  |
| PUT    | `/api/accounts/{id}/withdraw`| `{ "amount": 100 }`                  | user/admin  |
| GET    | `/api/accounts`              | —                                      | user/admin  |
| DELETE | `/api/accounts/{id}`         | —                                      | admin only  |

#### cURL Examples 🧪

```bash
# Create account
curl -u user:user123 -H "Content-Type: application/json" \
  -d '{"accountHoldername":"Alice","balance":1000}' \
  -X POST http://localhost:8080/api/accounts

# Get account by id
curl -u user:user123 http://localhost:8080/api/accounts/1

# Deposit
curl -u user:user123 -H "Content-Type: application/json" \
  -d '{"amount":250}' \
  -X PUT http://localhost:8080/api/accounts/1/deposit

# Withdraw
curl -u user:user123 -H "Content-Type: application/json" \
  -d '{"amount":100}' \
  -X PUT http://localhost:8080/api/accounts/1/withdraw

# List all
curl -u user:user123 http://localhost:8080/api/accounts

# Delete (admin only)
curl -u admin:admin123 -X DELETE http://localhost:8080/api/accounts/1
```

### Build and Test 🧱

```bash
# From the module directory
cd banking-app

# Build
./mvnw clean package

# Run tests
./mvnw test
```

### Troubleshooting 🩺
- Cannot connect to MySQL: ensure the `banking_app` database exists and credentials in `application.properties` are correct.
- Port already in use: change `server.port` in `application.properties` or free the port.
- 401 Unauthorized: include Basic auth credentials; delete requires admin.

### License 📄
This project is for learning/demo purposes. Add a license if you plan to distribute.


