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

Package root: `br.udesc.ceavi.progii.avicena`. Six vertical slices have been migrated
to a Clean Architecture layering; the original NetBeans-generated `model/` package is
gone entirely, and only a small Swing shell plus a few standalone old-style listeners
remain unmigrated. There is no more "old vs. new" bridging — everything reads and
writes through the Clean Architecture repositories now.

### Migrated verticals: `patient/`, `doctor/`, `nurse/`, `receptionist/`, `appointment/`

**Patient, Doctor, Nurse, Receptionist** — four uniform vertical slices, none of them
import from each other's `domain`:

- **`domain/`** — a plain domain class (`Patient`, `Doctor`, `Nurse`, `Receptionist`),
  its `*Repository` interface, and an `Invalid*DataException`. `Address` and
  `MaritalStatus` live in `patient.domain` and are imported by the other three —
  the one deliberate cross-package dependency in the whole migrated codebase. If a
  future migration needs its own address-like value object, follow this precedent
  (reuse from `patient.domain`) rather than duplicating it.
- **`usecase/`** — thin wrappers around the repository: `Register*`, `List*`,
  `Delete*`. No business logic beyond what the domain constructor already validates.
- **`infrastructure/persistence/`** — `*Entity` (the real `@Entity` JPA class,
  standalone, does not extend anything shared), `*Mapper` (package-private,
  domain↔entity conversion), `*JpaRepository` (implements the domain repository,
  opens/closes an `EntityManager` per call via `PersistenceConfig`).
- **`infrastructure/ui/`** — `*RegistrationFrame` (Swing form), `*CrudController`
  (wires Novo/Gravar/Excluir/Cancelar to the use cases), `*SearchController` (CPF
  search, list-all-then-filter, no dedicated query), `Register*MenuListener` (opens the
  frame from the main menu). `patient.infrastructure.ui` additionally owns
  `AddressEntryFrame`/`AddressEntryController`/`AddAddressListener` — the "Add
  Address" popup shared by all four registration screens. The controller does no
  database work itself; it just captures form input into an in-memory `Address`, and
  each entity's `@ManyToOne address` field is `cascade = CascadeType.PERSIST`, so
  saving the parent persists the address with it.

**Appointment** (with `Diagnosis` nested inside it, not a sibling package) — the first
migrated entity with real cross-aggregate relationships, and it deviates from the
four-entity template on purpose:

- `appointment.domain.Appointment` holds `patientId`/`doctorId`/`nurseId` as `Long`
  fields rather than embedding `Patient`/`Doctor`/`Nurse` domain objects — it
  references those aggregates by ID, not by object, so `Appointment` doesn't need
  their repositories. `appointment.infrastructure.persistence.AppointmentEntity`
  still holds real `@ManyToOne` JPA relationships to `PatientEntity`/`DoctorEntity`/
  `NurseEntity` (JPA relationships stay JPA-managed even when the domain layer only
  keeps IDs).
- `appointment.domain.Diagnosis` lives inside `appointment` rather than as its own
  top-level package, because diagnoses have no independent use case — nothing lists
  or searches diagnoses on their own, they're only ever created against an existing
  appointment. Its `DiagnosisRepository` only exposes `save`/`delete`, no `findAll`,
  matching that reality.
- `DiagnosisEntity` (`diagnostico_primario` table) has a
  `@ManyToOne(cascade = CascadeType.ALL) FinalDiagnosisEntity` (`diagnostico_final`
  table) — the one cascade relationship in the codebase that also cascades removal,
  mirroring the original two-table schema.
- `infrastructure/ui/` has `AppointmentRegistrationFrame`/`AppointmentCrudController`,
  `AppointmentListFrame` (extends `FrameSemCRUD`, not `FrameCRUD` — it's a read-only
  table, not a CRUD form), and `DiagnosisRegistrationFrame`/`DiagnosisCrudController`.
  `DiagnosisRegistrationFrame` replicates the original two-panel `CardLayout` UI
  (Primary Diagnosis / Final Diagnosis in one window) and embeds the "Generate
  Receipt" button that triggers `BtGerarReceiraListener`. `PatientHistoryFrame`/
  `PatientHistorySearchController`/`ShowPatientHistoryMenuListener` are the real,
  `AppointmentEntity`-backed replacement for the old fake-data
  `FrameHistoricoPaciente` stub — CPF search, list-all-then-filter, same shape as
  the four staff/patient `*SearchController`s.

### What's left outside the six migrated packages

- **`control/dao/`** — only `PersistenceConfig` (the `EntityManagerFactory` factory,
  reads `AVICENA_DB_URL`/`USER`/`PASSWORD` env vars or system properties, falls back
  to `persistence.xml` defaults). Every old-style DAO (`JPADAO`, the `DAO` interface,
  and every per-entity DAO) has been deleted. `control/exceptions/` is gone entirely
  now too — it held only `ValorNuloException`/`ValorIncorretoException`, both dead
  once the DAOs that threw them (`EnderecoDAO`, `ConsultaDAO`) were removed.
- **`control/listenersMenu/`** — `MenuActionListener` (abstract base for menu-bar
  actions), `MenuSobreListener` ("About" dialog), and `BtGerarReceiraListener`
  (generates the prescription PDF via iText, wired from
  `DiagnosisRegistrationFrame`; writes to a hardcoded relative path — note the code
  writes `Receira-Avicena.pdf`, transposed letters, not `Receita-Avicena.pdf`). There
  is no more `control/listenersCRUD/` — every old-style CRUD listener has been deleted
  along with the entity it used to serve.
- **`view/frames/`** — `FrameCRUD` (abstract template for entity CRUD screens, shared
  `CRUDActionPanel` for Novo/Gravar/Excluir/Cancelar) and `FrameSemCRUD` (template for
  read-only screens) are still the active base classes every registration/list screen
  extends, migrated or not.
- **`view/principal/`** — `FrameSistema` (MDI main window) and `MenuPrincipal` (menu
  bar, wires every `Register*MenuListener`/`List*MenuListener` from the six migrated
  packages alongside the handful of old-style listeners above).

## Known issues / refactor targets

Since this repo's purpose is refactoring practice, these are the load-bearing quirks
worth knowing before touching code:

- Hardcoded default DB credentials in `src/main/resources/META-INF/persistence.xml`
  (overridable via env var/system property, see Build & run, but the default itself is
  still a plaintext password in source).
- Test coverage is thin outside the six migrated packages: `PersistenceConfigTest` and
  `BtGerarReceiraListenerTest` are the only tests left in `control/`; `view/frames/`
  has none except `AppointmentListFrameTest`.

### Next steps

With every entity migrated and the old `model`/DAO/listener layers gone, the codebase
is close to uniformly Clean Architecture. What's left:

1. **Module boundaries.** Splitting `patient`/`doctor`/`nurse`/`receptionist`/
   `appointment` into separate Gradle modules is now realistic — the package
   boundaries already exist and only `patient.domain.Address`/`MaritalStatus` cross
   between them. This was previously blocked on DAO duality and the address
   duplication; both are resolved.
2. **Appointment registration UX** — `AppointmentRegistrationFrame` currently stores
   patient/doctor/nurse by ID only; the combo boxes show names at selection time but
   nothing re-displays them by name after the fact (e.g. on `AppointmentListFrame`,
   which does look them up via the JPA relationship — the registration frame doesn't
   need to, but it's worth knowing the two screens get names two different ways).
   Not broken, just worth being aware of before extending either screen.
