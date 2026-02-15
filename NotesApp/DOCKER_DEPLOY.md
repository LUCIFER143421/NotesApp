# Docker deployment

## Run locally with Docker Compose

```bash
docker compose up --build -d
```

App: `http://localhost:8080`

Stop:

```bash
docker compose down
```

Stop and remove DB volume:

```bash
docker compose down -v
```

## Deploy for free

### Option 1 (recommended): Render + Neon (free PostgreSQL)
1. Push this repo to GitHub.
2. Create a free PostgreSQL database on Neon and copy connection details.
3. On Render, create a **Web Service** connected to this repo.
4. Build command:
   ```bash
   docker build -t notesapp .
   ```
5. Start command:
   ```bash
   java -jar /app/app.jar
   ```
6. Set environment variables in Render:
   - `SPRING_PROFILES_ACTIVE=prod`
   - `SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db>?sslmode=require`
   - `SPRING_DATASOURCE_USERNAME=<user>`
   - `SPRING_DATASOURCE_PASSWORD=<password>`

### Option 2: Railway
- Deploy from GitHub with Dockerfile.
- Add a PostgreSQL service.
- Map the same Spring datasource environment variables.
