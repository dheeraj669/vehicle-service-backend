# Vehicle Service Management System - Backend API

Production-ready Spring Boot backend for the Vehicle Service Management System.

---

## 🛠️ Tech Stack
- **Framework**: Spring Boot 3.3.4
- **Language**: Java 17 / 21
- **Database**: H2 In-Memory (default for zero-setup deployment) & MySQL supported
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven

---

## 🚀 How to Run Locally

### Prerequisites
- JDK 17 or higher
- Maven (or use bundled wrapper)

```bash
# Using Maven
mvn clean spring-boot:run

# Or with custom port
mvn clean spring-boot:run -Dspring-boot.run.arguments="--server.port=8085"
```

The API will be available at:
- **API Base**: `http://localhost:8085/api`
- **H2 DB Console**: `http://localhost:8085/h2-console` (JDBC URL: `jdbc:h2:mem:vehicleservicedb`, User: `sa`, Password: blank)

---

## 🌐 Deploy to Cloud Providers

### Option 1: Deploy on Render (Recommended - Free Tier)
1. Push this repository to GitHub.
2. Sign in to [Render.com](https://render.com).
3. Click **New +** -> **Web Service**.
4. Connect this GitHub repository (`vehicle-service-backend`).
5. Choose **Docker** as Runtime (it will automatically pick up `Dockerfile`).
6. Set Environment Variables:
   - `PORT`: `8085` (Render automatically handles mapping)
7. Click **Create Web Service**. Render will build the Docker container and provide a live URL like:
   `https://vehicle-service-backend.onrender.com`

---

### Option 2: Deploy on Railway
1. Push this repository to GitHub.
2. Go to [Railway.app](https://railway.app).
3. Click **New Project** -> **Deploy from GitHub repo**.
4. Select `vehicle-service-backend`.
5. Railway automatically detects Dockerfile and deploys.
6. Under Settings -> Networking, click **Generate Domain**.

---

### Option 3: Deploy with Docker Locally or on VPS
```bash
# Build the Docker image
docker build -t vehicle-service-backend .

# Run the container
docker run -p 8085:8085 vehicle-service-backend
```

---

## 🔑 Environment Variables Reference

| Variable | Description | Default |
| :--- | :--- | :--- |
| `PORT` | HTTP Port for the application | `8085` |
| `SPRING_DATASOURCE_URL` | JDBC Connection String | `jdbc:h2:mem:vehicleservicedb;...` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `sa` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | (empty) |
| `SPRING_PROFILES_ACTIVE` | Active profile (`mysql` or default) | `default` |

---

## 📡 Core API Endpoints

- `GET /api/dashboard/stats` - Total customers, vehicles, revenue, pending services
- `GET /api/customers` - List customers (supports `?search=`)
- `POST /api/customers` - Create customer
- `GET /api/vehicles` - List vehicles (supports `?search=`)
- `POST /api/vehicles` - Create vehicle
- `GET /api/services` - List services (supports `?search=&status=`)
- `POST /api/services` - Create service record
- `PATCH /api/services/{id}/status` - Update service status
