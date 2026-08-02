# Avicena

Java Swing desktop app for clinic management (pacientes, médicos, enfermeiros,
atendentes, consultas, diagnósticos, receitas).

## Requirements

- Docker (with Compose)
- No local JDK required — the Gradle wrapper provisions Java 17 automatically

## Running locally

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

## Configuration

Default connection settings (matching `docker-compose.yml`) live in
`src/main/resources/META-INF/persistence.xml`. Override them with environment
variables if you're pointing at a different database:

- `AVICENA_DB_URL`
- `AVICENA_DB_USER`
- `AVICENA_DB_PASSWORD`

## Development

Run the test suite (needs the database running — see above):

```bash
./gradlew test
```

Run a single test class:

```bash
./gradlew test --tests "br.udesc.ceavi.progii.avicena.control.dao.PacienteDAOTest"
```

Build without running:

```bash
./gradlew build
```

See `CLAUDE.md` for architecture notes and known issues.
