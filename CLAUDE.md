# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

Avicena is a Java Swing desktop app for clinic management (patients, doctors, nurses,
receptionists, appointments, diagnoses, prescriptions). It's an academic project
(UDESC, Programming II course), originally a NetBeans Ant project, persisted via
JPA/Hibernate to PostgreSQL. Original requirements are in `REQUIREMENTS.md`.

This repo is used for **refactoring practice/training**, not active feature delivery.
See "Known issues / refactor targets" below for where the interesting work is.

## Build & run

Gradle (Kotlin DSL: `build.gradle.kts`, `settings.gradle.kts`), Java 17 via a Gradle
toolchain (auto-provisioned through `foojay-resolver-convention`). `mise.toml` pins
`temurin-17` for local use. Standard tasks: `./gradlew build`, `./gradlew run`,
`./gradlew test`, `./gradlew clean`.

Entry point: `br.udesc.ceavi.progii.avicena.main.AvicenaMain`. It calls
`PersistenceConfig.initialize()` before opening `MainFrame`, so Flyway runs and the
`EntityManagerFactory` boots once, at startup. A failure shows an error dialog and
exits with status 1.

### Database configuration

There are no default credentials anywhere in source. `PersistenceConfig` requires
`AVICENA_DB_URL`, `AVICENA_DB_USER` and `AVICENA_DB_PASSWORD`, resolved in this order:
Java system property, env var, then a `.env` file in the working directory (parsed by
`DotenvLoader`). A missing value throws `IllegalStateException`. For local runs:

```
cp .env.example .env   # then set a real password
docker compose up -d   # postgres:16-alpine, reads the same AVICENA_DB_* vars
./gradlew run
```

`PersistenceConfig.entityManagerFactory()` returns one lazily created, shared
`EntityManagerFactory` (closed by a shutdown hook registered in `initialize()`). Every
`*JpaRepository` grabs it in its constructor and opens/closes an `EntityManager` per
call. `createEntityManagerFactory()` still builds a fresh, unshared factory.

### Schema

Flyway owns the schema (`src/main/resources/db/migration/`, `V1` to `V12`), run by
`PersistenceConfig` before the persistence unit boots. Hibernate's schema-generation
action is unset. Flyway runs with `baselineOnMigrate(true)`/`baselineVersion("1")`, so
a database that already has the pre-Flyway schema baselines instead of re-running `V1`.

The #43 schema redesign is fully rolled out (#48, closed): every table has English
names, all four person roles share `people`, and lookups (`marital_statuses`,
`urgency_statuses`, `departments`, `specialties`, `roles`) replace raw ordinals and
free text. Migrations that rename live-referenced tables (`addresses`, `appointments`)
use `ALTER ... RENAME` to keep data. The others drop and recreate.

### Tests

Real integration tests against Postgres, no mocks. `PostgresContainerExtension` (test
sources, `control/dao/`, auto-registered for every test class via the service loader)
starts one Testcontainers Postgres for the whole suite and points the `AVICENA_DB_*`
system properties at it, so `./gradlew test` needs no docker-compose. Before each test
it truncates every `public` table (`RESTART IDENTITY CASCADE`) except the seeded ones
listed in `SEEDED_TABLES` (`marital_statuses`, `urgency_statuses`, `departments`,
`roles`, `flyway_schema_history`). **A new migration that seeds a lookup table must
add it to `SEEDED_TABLES`**, or the seed rows get wiped after the first test.

Use case tests run against `InMemory*Repository` fakes in each `usecase/` test package.

### CI

`.github/workflows/ci.yml` (GitHub Actions) runs `./gradlew build` on every push and PR
to `master`: Spotless check (`palantir-java-format`, fix locally with
`./gradlew spotlessApply`) plus the full test suite. Branch protection requires it to
pass and requires the branch to be up to date with `master` before merging.

## Architecture

Package root: `br.udesc.ceavi.progii.avicena`. Everything persistent lives in six
Clean Architecture packages. Only a small Swing shell sits outside them.

### Person verticals: `patient/`, `doctor/`, `nurse/`, `receptionist/`

Four uniform slices, none importing another's `domain`:

- **`domain/`**: plain domain class, its `*Repository` interface, and an
  `Invalid*DataException`. `Address` and `MaritalStatus` live in `patient.domain` and
  are imported by the other three. This is the one deliberate cross-package dependency;
  reuse it rather than duplicating an address-like value object.
- **`usecase/`**: thin `Register*`, `List*`, `Delete*` wrappers around the repository.
- **`infrastructure/persistence/`**: `*Entity` (JPA), package-private `*Mapper`
  (domain to entity), `*JpaRepository`. Each role entity (`patients`, `doctors`,
  `nurses`, `receptionists`) holds a `@OneToOne(cascade = {PERSIST, REMOVE})
  PersonEntity` (table `people`, in `patient.infrastructure.persistence`) plus
  delegating getters (`getName()`, `getCpf()`, ...) used by appointment screens.
  `PersonEntity` has a `@ManyToOne(cascade = PERSIST)` to `AddressEntity` (`addresses`)
  and a `MaritalStatusEntity` (`marital_statuses`). Role-specific columns stay on the
  role table: Doctor `crm` plus a `SpecialtyEntity` FK, Nurse
  `formation`/`hoursCompleted`, Receptionist
  `workHours`/`salary`/`overtimeHours`/`laborCardNumber`.
- **`infrastructure/controller/`**: `*Controller` (`register(Form)`, `delete`,
  `findByCpf` as list-all-then-filter). UI-agnostic interface adapter, see
  "Composition root".
- **`infrastructure/ui/swing/`**: `*RegistrationFrame` (Swing form, owns its buttons,
  dialogs and the record on screen) and `Register*MenuListener`. Each UI technology
  gets its own subpackage under `ui/` (a future `ui/compose` or web adapter sits next to
  `swing`). `patient.infrastructure.ui.swing` also owns the "Add Address" popup
  (`AddressEntryFrame`/`AddAddressListener`), one instance per registration screen. It
  hands the `Address` it builds to the screen's `useAddress`; the cascade persists it
  with the parent.

Lookup entities that are closed, pre-seeded sets (`MaritalStatusEntity`,
`UrgencyStatusEntity`, `RoleEntity`) have no `@GeneratedValue` and are only read by
`code`. `specialties` is the exception: it is open-ended, so `Doctor` keeps
`specialty` as a `String` and `DoctorJpaRepository` finds or creates the
`SpecialtyEntity` by name on save.

### `appointment/`

Deviates from the person template on purpose:

- `Appointment` references patient/doctor/nurse by `Long` ID, not by domain object.
  `AppointmentEntity` (`appointments`) still holds real `@ManyToOne`s to
  `PatientEntity`/`DoctorEntity`/`NurseEntity`/`UrgencyStatusEntity`, with
  `appointment_date`/`appointment_time` mapped as `LocalDate`/`LocalTime`.
  `appointments.department_id` exists (DB default `1`, the single seeded department)
  but is deliberately unmapped: nothing reads or writes it yet.
- `Diagnosis` is nested here, not a sibling package, because it has no independent use
  case. `DiagnosisRepository` only exposes `save`/`delete`. `DiagnosisEntity`
  (`diagnoses`) has a `@ManyToOne(cascade = ALL) FinalDiagnosisEntity`
  (`final_diagnoses`), the one cascade in the codebase that also cascades removal.
- `infrastructure/controller/`: `AppointmentController`, `DiagnosisController` and
  `PatientHistoryController` (CPF search over appointments).
- `infrastructure/ui/swing/`: `AppointmentRegistrationFrame`, `AppointmentListFrame`
  (extends `ReadOnlyFrame`, read-only table), `DiagnosisRegistrationFrame` (two-panel
  `CardLayout`: primary/final diagnosis), `PatientHistoryFrame`, and
  `GeneratePrescriptionListener` (prescription PDF via iText, triggered from
  `DiagnosisRegistrationFrame`).

### `auth/`: persistence only

`auth/infrastructure/persistence/` holds `UserEntity` (`users`, `@OneToOne` to
`PersonEntity` with **no cascade**, a user doesn't own its person), `RoleEntity`
(`roles`, seeded `ADMIN`/`DOCTOR`/`NURSE`/`RECEPTIONIST`) and `PermissionEntity`
(`permissions`, empty). `role_permissions`/`user_roles` are plain
`@ManyToMany @JoinTable`s. There is no `auth/domain`, `usecase` or `ui`: login,
sessions and permission checks are unbuilt.

### Composition root

`AvicenaMain` is the only place that creates `*JpaRepository` instances. After
`PersistenceConfig.initialize()` it builds every repository, use case, frame, controller
and menu listener once, and hands each one its collaborators through constructors.
**Screens receive dependencies, never fetch them**: no class under `infrastructure/ui`,
`view/` or `control/menu` creates a repository or uses a static `getInstance()`.
Frames are created once and reused, so a menu item for an open screen just brings it to
the front. Each vertical describes its own main menu items: every `*MenuListener`
extends `MenuActionListener` and passes its `MainMenu.Section`, label, icon and
tooltip to the base constructor. `MainMenu` only knows the sections (fixed bar
order) and a single `add(MenuActionListener)`. `AvicenaMain` adds items in display
order, which is why its vertical blocks run Patient, Receptionist, Doctor, Nurse.

**The UI knows the controller, never the other way around.** Each frame receives its
`*Controller` and handles buttons, `JOptionPane` dialogs and the record currently on
screen itself. Controllers import no Swing or AWT: they take a `Form` record of raw
input values (strings, as a web form would send them), parse numbers, call the use
cases and return the domain object or throw the domain's `Invalid*DataException`. A
web front end should reuse them unchanged. Controller tests drive them directly against
`InMemory*Repository` copies in the `infrastructure/controller` test package, with no
frame and no Postgres. Nothing under `infrastructure/controller` may import from `ui`.

`AppointmentListFrame`, `PatientHistoryFrame`, `DiagnosisRegistrationFrame` and
`GeneratePrescriptionListener` still query `AppointmentEntity` directly through
`PersistenceConfig.entityManagerFactory()`. Removing that needs new use cases and
repository queries.

### Outside the six packages

- **`control/dao/`**: `PersistenceConfig` and `DotenvLoader`.
- **`control/menu/`**: `MenuActionListener` (abstract base for menu items)
  and `AboutMenuListener` ("About" dialog).
- **`view/frames/`**: `CrudFrame` (abstract template for CRUD screens) and
  `ReadOnlyFrame` (read-only screens), base classes for every screen.
  `view/panels/CrudActionPanel` is the shared New/Save/Delete/Cancel bar.
- **`view/main/`**: `MainFrame` (MDI main window) and `MainMenu` (menu
  bar: fixed sections, items added from `AvicenaMain`).

## Conventions

- **Everything is English**: identifiers, packages, comments, test names and data,
  user-facing text (labels, dialogs, PDF content) and docs. No Portuguese anywhere.
  Already-applied Flyway migrations are the one exception: they reference the old
  Portuguese table names they renamed, and editing them would break their checksums.

## Known issues / refactor targets

- **UI layer is the least refactored part.** Doctor got a cleanup (#85): an `addField` helper in `DoctorRegistrationFrame` replacing `GridBagConstraints`
  boilerplate. Patient, Nurse and Receptionist UIs don't have the helper yet.
- `DotenvLoader` splits each line on every `=`, so a value containing `=` is truncated.
  No quote handling either.
- `GeneratePrescriptionListener` writes `Avicena-Prescription.pdf` to a hardcoded
  relative path.
- `AppointmentRegistrationFrame` stores patient/doctor/nurse by ID only; names are
  shown at selection time but not re-displayed afterwards. `AppointmentListFrame` gets
  names through the JPA relationships instead.

### Next steps

1. Build the `auth` vertical (domain, login, role-based menu) on top of the existing
   tables. The menu is ready for it: filter `MenuActionListener`s by role before
   `MainMenu.add`.
2. Apply the #85 Doctor UI cleanup to Patient, Nurse and Receptionist.
3. Split the verticals into Gradle modules. Package boundaries already exist; only
   `patient.domain.Address`/`MaritalStatus` and `PersonEntity` cross them.
