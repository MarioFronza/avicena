# Avicena

Java Swing desktop app for clinic management (patients, doctors, nurses,
receptionists, appointments, diagnoses, prescriptions).

## Requirements

- Docker (with Compose)
- No local JDK required — the Gradle wrapper provisions Java 17 automatically

## Running locally

Copy the example env file and fill in real values:

```bash
cp .env.example .env
```

Start the database:

```bash
docker compose up -d
```

Build and run the app:

```bash
./gradlew run
```

The first run creates the database schema automatically. Data persists across
container restarts (named volume).

The database is named `avicena`. Volumes created before that rename hold a
database called `AvicenaBD` instead: either point `AVICENA_DB_URL` at
`.../AvicenaBD` in your `.env`, or recreate the volume with
`docker compose down -v` (this deletes its data).

## Configuration

No credentials are committed anywhere in this repo. Connection settings are
resolved at runtime, in order: a Java system property, an environment
variable, then the `.env` file at the project root (see `.env.example`).
Nothing resolving throws immediately instead of falling back to a default.

- `AVICENA_DB_URL`
- `AVICENA_DB_USER`
- `AVICENA_DB_PASSWORD`

## Development

Run the test suite (self-contained — starts its own Testcontainers Postgres,
no `docker compose` needed):

```bash
./gradlew test
```

Run a single test class:

```bash
./gradlew test --tests "br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfigTest"
```

Build without running:

```bash
./gradlew build
```

See `CLAUDE.md` for architecture notes and known issues.
