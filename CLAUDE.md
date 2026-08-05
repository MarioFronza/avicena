# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

Avicena is a Java Swing desktop app for clinic management (patients, doctors, nurses,
receptionists, appointments, diagnoses, prescriptions). It's an academic project
(UDESC, Programação II course) built as a NetBeans "Java Application" (Ant-based),
persisted via JPA/EclipseLink to PostgreSQL. Original requirements are in
`REQUIREMENTS.md`.

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

Package root: `br.udesc.ceavi.progii.avicena`. Four entities have been migrated,
one-by-one, to a Clean Architecture layering; everything else is still the original
NetBeans-generated structure. Both styles coexist and bridge at the UI layer — see
below.

### Migrated entities: `patient/`, `doctor/`, `nurse/`, `receptionist/`

Each of these top-level packages is a self-contained vertical slice, none of them
import from each other's `domain`:

- **`domain/`** — a plain domain class (`Patient`, `Doctor`, `Nurse`, `Receptionist`),
  its `*Repository` interface, and an `Invalid*DataException`. `Address` and
  `MaritalStatus` live in `patient.domain` and are reused by the other three.
- **`usecase/`** — thin wrappers around the repository: `Register*`, `List*`,
  `Delete*`. No business logic beyond what the domain constructor already validates.
- **`infrastructure/persistence/`** — `*Entity` (the real `@Entity` JPA class,
  standalone, does not extend anything shared), `*Mapper` (package-private,
  domain↔entity conversion), `*JpaRepository` (implements the domain repository,
  opens/closes an `EntityManager` per call via `PersistenceConfig`).
- **`infrastructure/ui/`** — `*RegistrationFrame` (Swing form), `*CrudController`
  (wires Novo/Gravar/Excluir/Cancelar to the use cases), `*SearchController` (CPF
  search, list-all-then-filter, no dedicated query), `Register*MenuListener` (opens the
  frame from the main menu).

**The `Endereco`/`Address` bridge:** none of the four migrated entities have their own
address input screen. Each `*CrudController.currentAddress()` reads the currently
selected address from `ListenerCRUDEndereco.getInstance().getEndereco()` — the
*old-style* address controller — and converts the old `Endereco` entity's fields into
the domain `Address` value object by hand. This means `model.Endereco` (old) and
`patient.infrastructure.persistence.AddressEntity` (new) both map the same `endereco`
table as two separate JPA entity classes; anyone touching address handling needs to
know both exist.

### Not yet migrated: `model/`, `control/`, `view/`

- **`model/`** — `Consulta` (an appointment; `@ManyToOne` references into
  `PatientEntity`, `NurseEntity`, `DoctorEntity` from the migrated packages — this is
  the other bridge point), `Endereco`, `DiagnosticoPrimario`/`DiagnosticoFinal`,
  `EstadoPaciente` (enum).
- **`control/dao/`** — `JPADAO<X> implements DAO` is generic and works
  (`em.persist`/`merge`/`remove`, one `EntityManager` per call, closed in `finally`).
  It's the only thing that actually persists `Consulta` — `ListenerCRUDConsulta` calls
  `ConsultaDAO.inserir()` for validation and `JPADAO.inserir()` separately for the real
  save. The other per-entity DAOs are stubs: `ConsultaDAO`/`EnderecoDAO`'s `inserir()`
  validates and returns `true` but never persists;
  `DiagnosticoPrimarioDAO`/`DiagnosticoFinalDAO`/`AgendaDAO` throw
  `UnsupportedOperationException` for every method. `control/exceptions/`
  (`ValorNuloException`, `ValorIncorretoException`) are thrown by `EnderecoDAO` and
  `ConsultaDAO`'s validation.
- **`control/listenersCRUD/`** — One class per not-yet-migrated entity
  (`ListenerCRUDConsulta`, `ListenerCRUDEndereco`, `ListenerCRUDDiagnostico`, ...),
  lazy singleton (`getInstance(...)`) wired to a `FrameCRUD`'s
  Novo/Gravar/Excluir/Cancelar buttons. `ListenerCRUDEndereco` additionally serves as
  the bridge the four migrated `CrudController`s depend on (see above) — it is live
  infrastructure, not legacy code to delete.
- **`control/listenersMenu/`** — `MenuActionListener` is the abstract base for
  menu-bar actions; concrete `Menu*Listener` classes open the corresponding
  `view/frames/*` internal frame. The four migrated entities have their own
  `Register*MenuListener` instead, under their own `infrastructure/ui/`.
- **`view/frames/`** — Swing `JInternalFrame`s. `FrameCRUD` is the abstract template
  for entity CRUD screens (embeds a shared `CRUDActionPanel` for the
  Novo/Gravar/Excluir/Cancelar buttons); `FrameSemCRUD` is the template for read-only
  screens (agenda, histórico, listagens, consultation list). `view/principal/FrameSistema`
  is the MDI main window hosting the menu bar and internal frames.
- **PDF generation** — `BtGerarReceiraListener` (in `listenersMenu/`) uses iText
  (`com.itextpdf:itextpdf`, a Gradle dependency) to generate a prescription PDF at
  runtime to a hardcoded relative path (note: the code writes `Receira-Avicena.pdf`,
  transposed letters, not `Receita-Avicena.pdf`).

## Known issues / refactor targets

Since this repo's purpose is refactoring practice, these are the load-bearing quirks
worth knowing before touching code (not an exhaustive list, but the ones that shape
architecture decisions):

- **DAO duality**, now scoped to what's left unmigrated: `JPADAO` (works) vs.
  `ConsultaDAO`/`EnderecoDAO` (validate-only stubs) vs.
  `DiagnosticoPrimarioDAO`/`DiagnosticoFinalDAO`/`AgendaDAO` (complete stubs, every
  method throws `UnsupportedOperationException`). Whether a given old-style entity
  actually persists depends on whether its `ListenerCRUD*` separately calls `JPADAO`.
- **Dual address entities** — `model.Endereco` and
  `patient.infrastructure.persistence.AddressEntity` both map the `endereco` table.
  The four migrated `CrudController`s bridge through `ListenerCRUDEndereco` and a
  hand-written `Endereco`→`Address` conversion (see Architecture above). Consolidating
  this requires either migrating `Endereco` itself to Clean Architecture or having the
  migrated entities read `AddressEntity` directly — not yet decided.
- Static `EntityManager`/`EntityManagerFactory` fields, reassigned per instance and
  never closed, in `ConsultaDAO` (the other old-style DAOs use `JPADAO` for anything
  that actually needs to persist).
- Hardcoded default DB credentials in `src/main/resources/META-INF/persistence.xml`
  (overridable via env var/system property, see Build & run, but the default itself is
  still a plaintext password in source).
- Test coverage is thin outside the four migrated packages: DAOs and a couple of
  listener/frame bugfixes have integration tests; most of `control/listenersCRUD/` and
  `view/frames/` have none.

### Kotlin-migration sequencing

If/when part of this codebase moves to Kotlin, fix these first — each one makes the
migration harder the longer it's deferred, since Kotlin interop means migrating
class-by-class while both languages coexist:

1. **DAO duality.** Pick `JPADAO` or the per-entity DAOs for `Consulta`/`Endereco`/
   `Diagnostico`/`Agenda`, not both. A class that's only half-wired to persistence
   (validate-only stub, real save elsewhere) produces a Kotlin class with the same
   silent gap.
2. **Dual address entities.** Consolidate `Endereco` and `AddressEntity` before
   migrating either — migrating one while the other still exists just moves the
   redundancy into Kotlin.

Module boundaries (splitting `model`/`control`/`view` into separate Gradle modules,
alongside the four already-separate entity packages) are a reasonable next step after
that, so migration can happen module-by-module — worth a scoped follow-up rather than
doing it speculatively here.
