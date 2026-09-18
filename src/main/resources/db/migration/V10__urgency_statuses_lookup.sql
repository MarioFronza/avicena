-- Adds the urgency_statuses lookup table from the #43 schema redesign,
-- replacing consulta.estado_paciente (a raw ordinal with no @Enumerated
-- annotation - the exact problem this table exists to fix, same
-- rationale as marital_statuses). Closed, pre-seeded set matching
-- UrgencyStatus's four values - no runtime inserts, same shape as
-- marital_statuses/roles.

CREATE TABLE public.urgency_statuses (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    label character varying(255) NOT NULL,
    CONSTRAINT urgency_statuses_pkey PRIMARY KEY (id),
    CONSTRAINT urgency_statuses_code_key UNIQUE (code)
);

INSERT INTO public.urgency_statuses (id, code, label) VALUES
    (1, 'EMERGENCY', 'Emergency'),
    (2, 'URGENT', 'Urgent'),
    (3, 'SLIGHTLY_URGENT', 'Slightly urgent'),
    (4, 'NOT_URGENT', 'Not urgent');

ALTER TABLE public.consulta ADD COLUMN urgency_status_id bigint;

UPDATE public.consulta SET urgency_status_id = CASE estado_paciente
    WHEN 0 THEN 1
    WHEN 1 THEN 2
    WHEN 2 THEN 3
    WHEN 3 THEN 4
END;

ALTER TABLE public.consulta DROP CONSTRAINT consulta_estado_paciente_check;
ALTER TABLE public.consulta DROP COLUMN estado_paciente;

ALTER TABLE ONLY public.consulta
    ADD CONSTRAINT consulta_urgency_status_id_fkey FOREIGN KEY (urgency_status_id) REFERENCES public.urgency_statuses(id);
