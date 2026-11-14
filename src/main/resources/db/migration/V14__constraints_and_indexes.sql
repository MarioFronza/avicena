-- Housekeeping: the database enforces the same invariants as the domain, every foreign key
-- is indexed, and the sequences left behind by the old Portuguese tables are dropped.
-- Data risk: none changed. The checks below abort the migration with a message when existing
-- rows already break a new rule; fix those rows and run again.

DO $$
BEGIN
    IF EXISTS (SELECT cpf FROM public.people GROUP BY cpf HAVING COUNT(*) > 1) THEN
        RAISE EXCEPTION 'V14: people share a cpf; merge them before migrating';
    END IF;
    IF EXISTS (SELECT crm FROM public.doctors WHERE crm IS NOT NULL GROUP BY crm HAVING COUNT(*) > 1) THEN
        RAISE EXCEPTION 'V14: doctors share a crm; fix them before migrating';
    END IF;
    IF EXISTS (SELECT final_diagnosis_id FROM public.diagnoses WHERE final_diagnosis_id IS NOT NULL
               GROUP BY final_diagnosis_id HAVING COUNT(*) > 1) THEN
        RAISE EXCEPTION 'V14: diagnoses share a final diagnosis; split them before migrating';
    END IF;
    IF EXISTS (SELECT 1 FROM public.appointments WHERE patient_id IS NULL OR urgency_status_id IS NULL
               OR appointment_date IS NULL OR appointment_time IS NULL) THEN
        RAISE EXCEPTION 'V14: appointments without patient, urgency, date or time exist; complete or delete them';
    END IF;
    IF EXISTS (SELECT 1 FROM public.diagnoses WHERE appointment_id IS NULL) THEN
        RAISE EXCEPTION 'V14: diagnoses without an appointment exist; link or delete them';
    END IF;
END $$;

ALTER TABLE ONLY public.people ADD CONSTRAINT people_cpf_key UNIQUE (cpf);
ALTER TABLE ONLY public.doctors ADD CONSTRAINT doctors_crm_key UNIQUE (crm);
ALTER TABLE ONLY public.diagnoses ADD CONSTRAINT diagnoses_final_diagnosis_id_key UNIQUE (final_diagnosis_id);

ALTER TABLE public.appointments
    ALTER COLUMN patient_id SET NOT NULL,
    ALTER COLUMN urgency_status_id SET NOT NULL,
    ALTER COLUMN appointment_date SET NOT NULL,
    ALTER COLUMN appointment_time SET NOT NULL;
ALTER TABLE public.diagnoses ALTER COLUMN appointment_id SET NOT NULL;

ALTER TABLE ONLY public.nurses ADD CONSTRAINT nurses_training_hours_check CHECK (training_hours >= 0);
ALTER TABLE ONLY public.receptionists ADD CONSTRAINT receptionists_work_hours_check CHECK (work_hours >= 0);
ALTER TABLE ONLY public.receptionists ADD CONSTRAINT receptionists_overtime_hours_check CHECK (overtime_hours >= 0);
ALTER TABLE ONLY public.receptionists ADD CONSTRAINT receptionists_salary_check CHECK (salary >= 0);
ALTER TABLE ONLY public.diagnoses ADD CONSTRAINT diagnoses_blood_pressure_check CHECK (blood_pressure >= 0);
ALTER TABLE ONLY public.diagnoses ADD CONSTRAINT diagnoses_temperature_check CHECK (temperature >= 0);
ALTER TABLE ONLY public.diagnoses ADD CONSTRAINT diagnoses_height_check CHECK (height >= 0);
ALTER TABLE ONLY public.diagnoses ADD CONSTRAINT diagnoses_weight_check CHECK (weight >= 0);

CREATE INDEX people_address_id_idx ON public.people (address_id);
CREATE INDEX people_marital_status_id_idx ON public.people (marital_status_id);
CREATE INDEX doctors_specialty_id_idx ON public.doctors (specialty_id);
CREATE INDEX appointments_patient_id_idx ON public.appointments (patient_id);
CREATE INDEX appointments_doctor_id_idx ON public.appointments (doctor_id);
CREATE INDEX appointments_nurse_id_idx ON public.appointments (nurse_id);
CREATE INDEX appointments_urgency_status_id_idx ON public.appointments (urgency_status_id);
CREATE INDEX appointments_department_id_idx ON public.appointments (department_id);
CREATE INDEX diagnoses_appointment_id_idx ON public.diagnoses (appointment_id);
CREATE INDEX role_permissions_permission_id_idx ON public.role_permissions (permission_id);
CREATE INDEX user_roles_role_id_idx ON public.user_roles (role_id);

DROP SEQUENCE IF EXISTS public.paciente_seq;
DROP SEQUENCE IF EXISTS public.medico_seq;
DROP SEQUENCE IF EXISTS public.enfermeiro_seq;
DROP SEQUENCE IF EXISTS public.atendente_seq;
