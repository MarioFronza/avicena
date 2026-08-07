-- Rolls out the people/patient shape from #43's schema redesign: a shared
-- `people` table (name/cpf/phone/address/marital_status) replaces the
-- columns duplicated across paciente/medico/enfermeiro/atendente, starting
-- with Patient. Doctor/Nurse/Receptionist stay on their old tables for now
-- (follow-up migrations), so `endereco` is left untouched here - it's still
-- in active use by those three.

CREATE TABLE public.marital_statuses (
    id bigint NOT NULL,
    code character varying(20) NOT NULL,
    label character varying(50) NOT NULL,
    CONSTRAINT marital_statuses_pkey PRIMARY KEY (id),
    CONSTRAINT marital_statuses_code_key UNIQUE (code)
);

-- No sequence: this table is seeded here and only ever read by the app
-- (looked up by code), never inserted into at runtime.
INSERT INTO public.marital_statuses (id, code, label) VALUES
    (1, 'SINGLE', 'Single'),
    (2, 'MARRIED', 'Married'),
    (3, 'DIVORCED', 'Divorced'),
    (4, 'WIDOWED', 'Widowed'),
    (5, 'OTHER', 'Other');

CREATE TABLE public.people (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    cpf character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    address_id bigint,
    marital_status_id bigint,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT people_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.people_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.people
    ADD CONSTRAINT people_address_id_fkey FOREIGN KEY (address_id) REFERENCES public.endereco(codigo_endereco);

ALTER TABLE ONLY public.people
    ADD CONSTRAINT people_marital_status_id_fkey FOREIGN KEY (marital_status_id) REFERENCES public.marital_statuses(id);

CREATE TABLE public.patients (
    id bigint NOT NULL,
    person_id bigint NOT NULL,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT patients_pkey PRIMARY KEY (id),
    CONSTRAINT patients_person_id_key UNIQUE (person_id)
);

CREATE SEQUENCE public.patients_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.patients
    ADD CONSTRAINT patients_person_id_fkey FOREIGN KEY (person_id) REFERENCES public.people(id);

-- consulta references paciente, which is about to be dropped. patients
-- starts empty (no backfill, per #48's scope - this is a from-scratch
-- academic project, no production data to preserve), so any existing
-- appointment pointing at a patient can no longer resolve one. Drop those
-- rows rather than leave them dangling.
DELETE FROM public.consulta WHERE codigo_paciente IS NOT NULL;

-- CASCADE removes consulta's FK to paciente regardless of its name - this
-- database's constraint names vary by when/how the table was created
-- (EclipseLink-era vs. this baseline's Hibernate-generated names).
DROP TABLE public.paciente CASCADE;

ALTER TABLE ONLY public.consulta
    ADD CONSTRAINT consulta_codigo_paciente_fkey FOREIGN KEY (codigo_paciente) REFERENCES public.patients(id);
