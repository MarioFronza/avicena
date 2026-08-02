# Clean Architecture Migration Template

This is the checklist for repeating the Patient migration (see `patient/`) on the
remaining Pessoa subtypes: Doctor (Medico), Nurse (Enfermeiro), Receptionist
(Atendente), and the Consulta/Diagnostico entities that reference them.

Read `patient/domain/`, `patient/usecase/`, `patient/infrastructure/` end to end
before starting the next entity. This doc is the checklist, that package is the
worked example.

## Package layout

For an entity `Foo`, create `br.udesc.ceavi.progii.avicena.foo` with three
sub-packages:

- `foo.domain` — plain Java, zero `javax.persistence.*` / `javax.swing.*` imports.
  The entity class itself, any value objects it owns, its repository interface
  (port), and its own exception type.
- `foo.usecase` — one class per operation (`RegisterFoo`, `ListFoos`,
  `DeleteFoo`, ...). Each depends only on `foo.domain` types plus the repository
  interface, never on a concrete JPA or Swing class. Only build use cases for
  operations that actually work in the current code — check whether the
  Portuguese DAO's `atualizar`/`deletar`/`pesquisarPorId` are real or dead
  `UnsupportedOperationException` stubs before reimplementing them (see
  CLAUDE.md's DAO duality note).
- `foo.infrastructure.persistence` — a standalone JPA `@Entity` (`FooEntity`,
  **not** extending `Pessoa` — see "Why not extend Pessoa" below), a mapper
  between `FooEntity` and the domain `Foo`, and a `FooJpaRepository`
  implementing the domain's repository interface.
- `foo.infrastructure.ui` — the translated Swing frame and CRUD controller,
  wired to the use cases instead of calling DAOs directly.

## Why not extend Pessoa

`Pessoa` is `@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)`, so each
subtype's table already has every inherited column. If the new JPA entity
extended `Pessoa`, it would inherit `getNome()`, `getTelefone()`, etc. directly
— Portuguese method names leaking onto a class whose whole point is being
English. Instead, map the new entity standalone, with `@Column(name = "...")`
pointing at the existing columns. Confirmed working for Patient without any
schema change.

## Step order (mirrors the Patient commits)

1. **Domain first.** Write `Foo`, its value objects, and the repository
   interface. Test the entity's own validation (whatever the old DAO's
   `inserir()` checked) with plain unit tests — no DB, no Swing.
2. **Use cases.** One RED/GREEN cycle per operation, against an
   `InMemoryFooRepository` test double. This is where you decide which
   operations are real (see the dead-stub warning above).
3. **JPA infrastructure.** `FooEntity`, its mapper, `FooJpaRepository`.
   Register `FooEntity` (and any new value-object entity) in
   `persistence.xml`. Test against Testcontainers Postgres, mirroring the old
   `FooDAOTest`.
   - If `Foo` has an enum field with no `@Enumerated` annotation in the
     original, EclipseLink defaults to ORDINAL storage. The new enum's
     constant order must match the old one exactly, or existing rows'
     meaning silently shifts.
4. **Swing UI.** Translate the frame/controller/menu-listener trio. Check
   whether the combo-box-driven fields were populated from
   `EnumType.values()` — if so, read `getSelectedItem()` cast to the enum
   instead of reproducing a hand-written index switch; that switch is exactly
   the kind of thing translation bugs hide in.
5. **External callers.** `grep -rn '\bFoo\b'` across `src/main` and `src/test`
   before deleting anything. Expect false positives (Portuguese string
   literals, unrelated classes whose name happens to contain "Foo"). For real
   callers:
   - If the caller is a JPA entity with a `@ManyToOne` to `Foo` (like
     `Consulta.paciente`), point it at `FooEntity`, not the domain `Foo` —
     the relationship still needs to be JPA-managed.
   - Only change the type/import and the specific method calls
     (`getNome()`→`getName()`, etc.). Don't rename the caller's own classes,
     methods, or fields — that's a different entity's migration.
6. **Delete the old Portuguese files.** Old model class, old DAO, old
   `ListenerCRUD*`, old `FrameCadastro*`, old `MenuCad*Listener`, old DAO
   test. Remove the old `<class>` entry from `persistence.xml`. Leave shared
   dependencies (`Endereco`, `EstadoCivil`, `Pessoa`) alone until every
   subtype that uses them has migrated.

## Shared dependencies

`Address`/`MaritalStatus` were translated once, during the Patient slice, and
now live in `patient.domain`. Reuse those from `foo.domain` rather than
retranslating `Endereco`/`EstadoCivil` per entity — but note they're still
also consumed, under their old Portuguese names, by every subtype that hasn't
migrated yet. Don't delete `Endereco`/`EstadoCivil`/`Pessoa` until Doctor,
Nurse, and Receptionist have all migrated.
