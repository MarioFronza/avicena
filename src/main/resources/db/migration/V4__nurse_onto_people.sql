-- Migrates Nurse onto the people foundation (V2), same shape as
-- Patient/Doctor. formation/hours_completed stay as plain columns on
-- nurses, mirroring how doctors kept crm/specialty.

CREATE TABLE public.nurses (
    id bigint NOT NULL,
    person_id bigint NOT NULL,
    formation character varying(255) NOT NULL,
    hours_completed integer NOT NULL,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT nurses_pkey PRIMARY KEY (id),
    CONSTRAINT nurses_person_id_key UNIQUE (person_id)
);

CREATE SEQUENCE public.nurses_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.nurses
    ADD CONSTRAINT nurses_person_id_fkey FOREIGN KEY (person_id) REFERENCES public.people(id);

-- consulta.codigo_enfermeiro referenced enfermeiro; V2 already deleted
-- every consulta row, so consulta is guaranteed empty here - no orphan
-- cleanup needed before repointing the FK.
DROP TABLE public.enfermeiro CASCADE;

ALTER TABLE ONLY public.consulta
    ADD CONSTRAINT consulta_codigo_enfermeiro_fkey FOREIGN KEY (codigo_enfermeiro) REFERENCES public.nurses(id);
