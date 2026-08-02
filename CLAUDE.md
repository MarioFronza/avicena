# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

Avicena is a Java Swing desktop app for clinic management (patients, doctors, nurses,
receptionists, appointments, diagnoses, prescriptions). It's an academic project
(UDESC, Programação II course) built as a NetBeans "Java Application" (Ant-based),
persisted via JPA/EclipseLink to PostgreSQL. Requirements and domain model
(`AvicenaRequisitos.txt`, ISO-8859-1 encoded) are in Portuguese.

This repo is being used for **refactoring practice/training**, not active feature
delivery — see "Known issues / refactor targets" below for where the interesting work is.

## Build & run

Gradle (Kotlin DSL: `build.gradle.kts`, `settings.gradle.kts`), Java 17 via a Gradle
toolchain (auto-provisioned through the `foojay-resolver-convention` plugin — no local
JDK 17 install required). Standard tasks: `./gradlew build`, `./gradlew run`,
`./gradlew test`, `./gradlew clean`.

Runtime Postgres is a Docker Compose service (`docker-compose.yml`,
`postgres:16-alpine`). `docker compose up -d` before `./gradlew run`. Connection
details in `src/main/resources/META-INF/persistence.xml` default to
`jdbc:postgresql://localhost:5432/AvicenaBD` / `postgres` / a hardcoded password
matching `docker-compose.yml`; override via `AVICENA_DB_URL` / `AVICENA_DB_USER` /
`AVICENA_DB_PASSWORD` env vars or matching Java system properties (system properties
win — see `PersistenceConfig`). `schema-generation.database.action=create` — EclipseLink
creates/updates the schema from `@Entity` classes on `EntityManagerFactory` creation,
no separate migration step.

Tests are real integration tests against Postgres, no mocks. A JUnit 5 extension
(`PostgresContainerExtension`, auto-registered for every test class via the service
loader) starts its own Testcontainers Postgres and points `AVICENA_DB_URL`/`USER`/
`PASSWORD` at it before any test runs, so `./gradlew test` works with no docker-compose
running at all — the docker-compose Postgres is for `./gradlew run`, not for tests.

CI (`.github/workflows/ci.yml`, GitHub Actions) runs `./gradlew build` — Spotless
formatting check (`palantir-java-format`, run `./gradlew spotlessApply` to fix
locally) plus the full test suite — on every push and PR to `master`. Branch
protection requires it to pass before merging.

Entry point: `br.udesc.ceavi.progii.avicena.main.AvicenaMain`, opens `FrameSistema`.

## Architecture

Package root: `br.udesc.ceavi.progii.avicena` (most code), with three exception
classes oddly living under a sibling root `br.udesc.ceavi.avicena.control.exceptions`
instead — a naming inconsistency from early development, not a deliberate split.

- **`model/`** — JPA entities. `Pessoa` is an abstract `@Entity` base
  (`InheritanceType.TABLE_PER_CLASS`) extended by `Paciente`, `Medico`, `Atendente`,
  `Enfermeiro`, each adding role-specific fields. `Consulta` links a `Paciente`,
  `Enfermeiro` and `Medico`; `DiagnosticoPrimario`/`DiagnosticoFinal` hang off a
  `Consulta`. `Endereco` is a shared `@ManyToOne` on `Pessoa`. `EstadoCivil` /
  `EstadoPaciente` are enums.

- **`control/dao/`** — Two *inconsistent, coexisting* persistence layers, not a single
  clean DAO layer:
  - `JPADAO<X> implements DAO` — a generic, working implementation
    (`em.persist`/`merge`/`remove`) shared across entities.
  - Per-entity DAOs (`PacienteDAO`, `MedicoDAO`, etc.) also `implements DAO<X>`, but
    are largely incomplete — e.g. `PacienteDAO.inserir()` only validates fields and
    returns `true`, it never calls `em.persist()`; `atualizar`/`deletar`/
    `pesquisarPorId` throw `UnsupportedOperationException`. Whether a given entity
    actually gets saved depends on which of the two DAOs its `ListenerCRUD*` calls for
    that operation — check both before assuming persistence works.
  - `JPADAO` holds a shared `EntityManagerFactory` singleton but opens/closes a fresh
    `EntityManager` per method call. The per-entity DAOs still hold their own
    **static** `EntityManager`/`EntityManagerFactory`, reassigned every time a new
    instance is constructed and never explicitly closed — reused static state across
    instances is still a latent bug source there, just not the closed-on-first-use
    variant `JPADAO` used to have.

- **`control/listenersCRUD/`** — The real controllers. One class per entity
  (`ListenerCRUDPaciente`, `ListenerCRUDConsulta`, ...), typically a lazy singleton
  (`getInstance(...)`) wired to a `FrameCRUD`'s Novo/Gravar/Excluir/Cancelar buttons in
  `addCRUDListeners()`. Each button's behavior is an inner `ActionListener` class that
  calls into `JPADAO` and/or the entity-specific DAO (inconsistently — see above).
  `control/listenersCRUD/exceptions/` (`IllegalOrphanException`,
  `NonexistentEntityException`, `PreexistingEntityException`) are unused leftovers from
  NetBeans' JPA-controller code-gen wizard — dead code.

- **`control/listenersMenu/`** — `MenuActionListener` is the abstract base for
  menu-bar actions; concrete `Menu*Listener` classes open the corresponding
  `view/frames/*` internal frame inside `FrameSistema`'s `JDesktopPane`.

- **`view/frames/`** — Swing `JInternalFrame`s. `FrameCRUD` is the abstract template
  for entity CRUD screens (embeds a shared `CRUDActionPanel` for the
  Novo/Gravar/Excluir/Cancelar buttons); `FrameSemCRUD` is the template for read-only
  screens (agenda, histórico, listagens). `view/principal/FrameSistema` is the MDI main
  window hosting the menu bar and internal frames.

- **PDF generation** — `BtGerarReceiraListener` (in `listenersMenu/`) uses iText
  (`com.itextpdf:itextpdf:5.5.9`, a Gradle dependency) to generate a prescription PDF
  (see `Receira-Avicena.pdf` at repo root for an example output).

## Known issues / refactor targets

Since this repo's purpose is refactoring practice, these are the load-bearing quirks
worth knowing before touching code (not an exhaustive list, but the ones that shape
architecture decisions):

- Duplicate/inconsistent DAO layer (`JPADAO` vs per-entity DAOs) — pick one pattern.
- Static `EntityManager`/`EntityManagerFactory` fields, reassigned per instance and
  never closed, in the per-entity DAOs (`ConsultaDAO`, `MedicoDAO`, `EnfermeiroDAO`,
  `PacienteDAO`).
- Hardcoded default DB credentials in `src/main/resources/META-INF/persistence.xml`
  (overridable via env var/system property, see Build & run, but the default itself is
  still a plaintext password in source).
- Dead code: `control/listenersCRUD/exceptions/*`, `Paciente.setEstadoCivil()` (throws
  `UnsupportedOperationException` unconditionally).
- Test coverage is thin: DAOs, `Medico.equals()`, and a couple of listener/frame
  bugfixes have integration tests; most of `control/listenersCRUD/` and `view/frames/`
  have none.

### Kotlin-migration sequencing

If/when part of this codebase moves to Kotlin, fix these first — each one makes the
migration harder the longer it's deferred, since Kotlin interop means migrating
class-by-class while both languages coexist:

1. **DAO duality.** Pick `JPADAO` or the per-entity DAOs, not both. Migrating a class
   that's only half-wired to persistence (per-entity DAO stubs that validate but never
   `em.persist()`) produces a Kotlin class with the same silent gap.
2. **Package split.** Consolidate `br.udesc.ceavi.avicena.control.exceptions` into
   `br.udesc.ceavi.progii.avicena` before it's a Kotlin package importing from a
   differently-rooted Java package for no reason.
3. **Dead code.** Delete `control/listenersCRUD/exceptions/*` and
   `Paciente.setEstadoCivil()` rather than translating dead code to Kotlin.

Module boundaries (splitting `model`/`control`/`view` into separate Gradle modules) are
a reasonable next step after that, so migration can happen module-by-module — worth a
scoped follow-up rather than doing it speculatively here.
