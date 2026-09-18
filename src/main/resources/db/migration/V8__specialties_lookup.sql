-- Adds the specialties lookup table from the #43 schema redesign,
-- normalizing doctors.specialty (freetext today) into a foreign key.
-- Unlike marital_statuses/roles/urgency_statuses, specialties is not a
-- closed, pre-seeded set - the app inserts a new row on demand the first
-- time a specialty name is used, so it needs a real sequence like any
-- other insertable entity.

CREATE TABLE public.specialties (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT specialties_pkey PRIMARY KEY (id),
    CONSTRAINT specialties_name_key UNIQUE (name)
);

CREATE SEQUENCE public.specialties_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.doctors ADD COLUMN specialty_id bigint;
ALTER TABLE public.doctors DROP COLUMN specialty;

ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT doctors_specialty_id_fkey FOREIGN KEY (specialty_id) REFERENCES public.specialties(id);
