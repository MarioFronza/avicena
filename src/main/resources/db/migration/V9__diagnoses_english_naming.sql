-- Renames diagnostico_primario/diagnostico_final to diagnoses/final_diagnoses
-- per the #43 schema redesign (#48). Java field/getter names on
-- DiagnosisEntity/FinalDiagnosisEntity were still Portuguese (the last
-- remaining Portuguese-named persistence code); this migration and the
-- matching entity changes close that gap. Drop-and-recreate, not ALTER
-- RENAME, following the same out-of-scope-data-preservation precedent as
-- the other person-table migrations in this series - appointment_id still
-- points at consulta, whose own rename is a later phase of this migration.

ALTER TABLE public.diagnostico_primario DROP CONSTRAINT fk10okludahu1xd8m6el94os205;
ALTER TABLE public.diagnostico_primario DROP CONSTRAINT fkb4yisa8fa3uy4pqm961r6q5bd;

DROP TABLE public.diagnostico_primario;
DROP TABLE public.diagnostico_final;
DROP SEQUENCE public.diagnostico_primario_seq;
DROP SEQUENCE public.diagnostico_final_seq;

CREATE TABLE public.final_diagnoses (
    id bigint NOT NULL,
    disease character varying(255),
    medications character varying(255),
    description character varying(255),
    exam_notes character varying(255),
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT final_diagnoses_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.final_diagnoses_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.diagnoses (
    id bigint NOT NULL,
    appointment_id bigint,
    final_diagnosis_id bigint,
    blood_pressure real,
    temperature integer,
    height integer,
    weight integer,
    medication_history character varying(255),
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT diagnoses_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.diagnoses_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.diagnoses
    ADD CONSTRAINT diagnoses_appointment_id_fkey FOREIGN KEY (appointment_id) REFERENCES public.consulta(codigo);

ALTER TABLE ONLY public.diagnoses
    ADD CONSTRAINT diagnoses_final_diagnosis_id_fkey FOREIGN KEY (final_diagnosis_id) REFERENCES public.final_diagnoses(id);
