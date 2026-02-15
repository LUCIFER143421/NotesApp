# Deployment Guide

## Run locally with Docker Compose

```bash
docker compose up --build -d
```

App: `http://localhost:8080`

Stop:

```bash
docker compose down
```

## Deploy on Render (recommended)

1. Push this repo to GitHub.
2. Create a PostgreSQL database (Render Postgres or Neon).
3. In Render, create a **Web Service** from this repo.
4. Use these commands:
   - **Build Command:** `./mvnw clean package -DskipTests`
   - **Start Command:** `java -jar target/NotesApp-0.0.1-SNAPSHOT.jar`
5. Set environment variables:
   - `SPRING_PROFILES_ACTIVE=prod`
   - `SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db>?sslmode=require`
   - `SPRING_DATASOURCE_USERNAME=<user>`
   - `SPRING_DATASOURCE_PASSWORD=<password>`
   - `JWT_SECRET=<a-long-random-secret>`

After deploy:
- Open `https://<your-render-domain>/` for the landing page.
- Register with `POST /auth/register` (or the form on `/`).
- Login with `POST /auth/login`.
