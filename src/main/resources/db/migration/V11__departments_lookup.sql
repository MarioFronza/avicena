-- Adds the departments lookup table from the #43 schema redesign. v1
-- ships with a single seeded row - no admin UI or domain concept exists
-- yet to pick a department, so consulta.department_id gets a DB-level
-- DEFAULT pointing at it. AppointmentEntity deliberately doesn't map
-- this column: nothing in the app reads or writes it yet, and mapping
-- an unused column would be speculative.

CREATE TABLE public.departments (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT departments_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.departments_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

INSERT INTO public.departments (id, name) VALUES (1, 'General Practice');

ALTER TABLE public.consulta ADD COLUMN department_id bigint NOT NULL DEFAULT 1;

ALTER TABLE ONLY public.consulta
    ADD CONSTRAINT consulta_department_id_fkey FOREIGN KEY (department_id) REFERENCES public.departments(id);
