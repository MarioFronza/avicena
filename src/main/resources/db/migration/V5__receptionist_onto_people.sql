-- Migrates Receptionist onto the people foundation (V2), same shape as
-- Patient/Doctor/Nurse. work_hours/salary/overtime_hours/labor_card_number
-- stay as plain columns on receptionists.
--
-- Unlike paciente/medico/enfermeiro, atendente has no FK from consulta -
-- receptionists were never linked to appointments in this schema - so no
-- FK repoint is needed here, just the table swap.

CREATE TABLE public.receptionists (
    id bigint NOT NULL,
    person_id bigint NOT NULL,
    work_hours integer NOT NULL,
    salary real NOT NULL,
    overtime_hours integer NOT NULL,
    labor_card_number integer NOT NULL,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT receptionists_pkey PRIMARY KEY (id),
    CONSTRAINT receptionists_person_id_key UNIQUE (person_id)
);

CREATE SEQUENCE public.receptionists_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.receptionists
    ADD CONSTRAINT receptionists_person_id_fkey FOREIGN KEY (person_id) REFERENCES public.people(id);

DROP TABLE public.atendente CASCADE;
