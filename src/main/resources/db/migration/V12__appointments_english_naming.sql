-- Final phase of #48's Appointment/Diagnosis migration: renames
-- consulta -> appointments (the last Portuguese-named table) and gives
-- appointment_date/appointment_time real date/time types, replacing the
-- dd/MM/yyyy and HH:mm strings. ALTER RENAME preserves existing data,
-- same precedent as endereco -> addresses, since this table is
-- live-referenced (diagnoses.appointment_id). Postgres tracks foreign
-- key and check constraints by attnum internally, so renaming a
-- referenced table/column never requires touching the referencing
-- side's constraint definition.

ALTER TABLE public.consulta RENAME TO appointments;
ALTER TABLE public.appointments RENAME CONSTRAINT consulta_pkey TO appointments_pkey;
ALTER TABLE public.appointments RENAME COLUMN codigo TO id;
ALTER TABLE public.appointments RENAME COLUMN codigo_paciente TO patient_id;
ALTER TABLE public.appointments RENAME COLUMN codigo_medico TO doctor_id;
ALTER TABLE public.appointments RENAME COLUMN codigo_enfermeiro TO nurse_id;
ALTER TABLE public.appointments RENAME COLUMN sintomas TO symptoms;

ALTER TABLE public.appointments ALTER COLUMN data TYPE date USING to_date(data, 'DD/MM/YYYY');
ALTER TABLE public.appointments RENAME COLUMN data TO appointment_date;

ALTER TABLE public.appointments ALTER COLUMN hora TYPE time USING to_timestamp(hora, 'HH24:MI')::time;
ALTER TABLE public.appointments RENAME COLUMN hora TO appointment_time;

ALTER TABLE public.appointments ADD COLUMN created_at timestamp NOT NULL DEFAULT now();
ALTER TABLE public.appointments ADD COLUMN updated_at timestamp NOT NULL DEFAULT now();

ALTER TABLE public.appointments RENAME CONSTRAINT consulta_codigo_paciente_fkey TO appointments_patient_id_fkey;
ALTER TABLE public.appointments RENAME CONSTRAINT consulta_codigo_medico_fkey TO appointments_doctor_id_fkey;
ALTER TABLE public.appointments RENAME CONSTRAINT consulta_codigo_enfermeiro_fkey TO appointments_nurse_id_fkey;
ALTER TABLE public.appointments RENAME CONSTRAINT consulta_urgency_status_id_fkey TO appointments_urgency_status_id_fkey;
ALTER TABLE public.appointments RENAME CONSTRAINT consulta_department_id_fkey TO appointments_department_id_fkey;

ALTER SEQUENCE public.consulta_seq RENAME TO appointments_seq;
