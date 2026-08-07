-- Migrates Doctor onto the people foundation (V2), same shape as Patient.
-- crm/specialty stay as plain columns on doctors - the #43 specialties
-- lookup table was only a "Should Have", deferred along with the
-- endereco -> addresses rename until all four verticals have moved off
-- their old tables.

CREATE TABLE public.doctors (
    id bigint NOT NULL,
    person_id bigint NOT NULL,
    crm character varying(255),
    specialty character varying(255),
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT doctors_pkey PRIMARY KEY (id),
    CONSTRAINT doctors_person_id_key UNIQUE (person_id)
);

CREATE SEQUENCE public.doctors_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT doctors_person_id_fkey FOREIGN KEY (person_id) REFERENCES public.people(id);

-- consulta.codigo_medico referenced medico; V2 already deleted every
-- consulta row (every appointment requires a patient, and V2 deleted all
-- of those), so consulta is guaranteed empty here - no orphan cleanup
-- needed before repointing the FK.
DROP TABLE public.medico CASCADE;

ALTER TABLE ONLY public.consulta
    ADD CONSTRAINT consulta_codigo_medico_fkey FOREIGN KEY (codigo_medico) REFERENCES public.doctors(id);
