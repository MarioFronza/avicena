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

This is a NetBeans `j2seproject` (`nbproject/project.xml`), driven by Ant
(`build.xml` imports the NetBeans-generated `nbproject/build-impl.xml`). Standard
targets: `ant` (build), `ant run`, `ant clean`, `ant jar`, `ant test`.

**The build does not currently work headless/CLI on a fresh machine:**
- `javac.classpath` in `nbproject/project.properties` references
  `${libs.eclipselink.classpath}` and `${libs.eclipselinkmodelgen.classpath}` —
  NetBeans *global* library definitions, not bundled in the repo. Without the NetBeans
  IDE (or a hand-built `~/.netbeans/<ver>/build.properties` defining those libraries),
  EclipseLink won't be on the classpath.
- `nbproject/private/private.properties` has **unresolved git merge-conflict markers**
  committed to source (multiple `<<<<<<< HEAD` / `=======` / `>>>>>>>` blocks), left
  over from past merges of each contributor's machine-local paths
  (`file.reference.postgresql-42.2.2.jar`, `user.properties.file`, etc.). This file is
  NetBeans-private/per-machine and normally gitignored — it shouldn't have been
  committed in the first place.
- `javac.source`/`javac.target` are `1.8`; the local JDK is 26. Expect
  `release version 8 not supported` or similar until this is bumped or an 8-compatible
  toolchain is configured.
- Two copies of the PostgreSQL driver and the iText PDF jar are vendored at the repo
  root (`postgresql-42.2.2.jar`, `itextpdf-5.5.9.jar`) and referenced by
  `file.reference.*` properties that also point at contributors' old local paths
  (`C:\Users\...`) — the repo-root copies are the ones that actually resolve.
- Runtime DB config is in `src/META-INF/persistence.xml`
  (`jdbc:postgresql://localhost:5432/AvicenaBD`, user `postgres`, **password
  hardcoded in source**). `schema-generation.database.action=create` — EclipseLink
  creates/updates the schema from the `@Entity` classes on `EntityManagerFactory`
  creation, there's no separate migration step.
- No `test/` sources exist despite `test.src.dir=test` being configured — `ant test`
  has nothing to run.
- Entry point: `br.udesc.ceavi.progii.avicena.main.AvicenaMain` (declared as
  `main.class` in `nbproject/project.properties`), just opens `FrameSistema`.

If you need a working build, the realistic paths are: (a) open in NetBeans so its
library manager resolves `libs.eclipselink.classpath`, or (b) migrate the classpath to
Maven/Gradle with explicit EclipseLink/PostgreSQL/iText coordinates — likely one of the
first refactors worth doing.

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
  - Both `JPADAO` and the per-entity DAOs hold their own **static**
    `EntityManager`/`EntityManagerFactory`, created fresh in the constructor and
    `.close()`d in a `finally` after the *first* call — reused static state plus
    closed-on-first-use is a latent bug source across concurrent/sequential DAO use.

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

- **PDF generation** — `BtGerarReceiraListener` (in `listenersMenu/`) uses the vendored
  `itextpdf-5.5.9.jar` to generate a prescription PDF (see `Receira-Avicena.pdf` at
  repo root for an example output).

## Known issues / refactor targets

Since this repo's purpose is refactoring practice, these are the load-bearing quirks
worth knowing before touching code (not an exhaustive list, but the ones that shape
architecture decisions):

- Duplicate/inconsistent DAO layer (`JPADAO` vs per-entity DAOs) — pick one pattern.
- Static, close-after-first-use `EntityManager` fields in DAOs.
- Hardcoded DB credentials in `src/META-INF/persistence.xml`.
- Broken/non-portable build (`nbproject/project.properties` depends on NetBeans global
  libraries; `nbproject/private/private.properties` has committed merge conflicts).
- Dead code: `control/listenersCRUD/exceptions/*`, `Paciente.setEstadoCivil()` (throws
  `UnsupportedOperationException` unconditionally).
- No automated tests.
