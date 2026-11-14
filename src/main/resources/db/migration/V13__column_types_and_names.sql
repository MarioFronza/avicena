-- Housekeeping: column names say what they hold and types fit their values.
--   nurses.formation/hours_completed      -> qualification/training_hours
--   diagnoses.medication_history          -> notes (it always held the free-text notes)
--   final_diagnoses.description           -> final_description
--   receptionists.salary real             -> numeric(10,2) (money must not be binary floating point)
--   receptionists.labor_card_number int   -> varchar(20) (a document number: may exceed int, keeps leading zeros)
--   diagnoses.temperature int             -> numeric(4,1) (37.5 was impossible)
-- Data risk: none. Renames keep data; every type change widens or exactly converts the current values.

ALTER TABLE public.nurses RENAME COLUMN formation TO qualification;
ALTER TABLE public.nurses RENAME COLUMN hours_completed TO training_hours;

ALTER TABLE public.diagnoses RENAME COLUMN medication_history TO notes;
ALTER TABLE public.final_diagnoses RENAME COLUMN description TO final_description;

ALTER TABLE public.receptionists ALTER COLUMN salary TYPE numeric(10, 2) USING salary::numeric(10, 2);
ALTER TABLE public.receptionists ALTER COLUMN labor_card_number TYPE character varying(20) USING labor_card_number::text;

ALTER TABLE public.diagnoses ALTER COLUMN temperature TYPE numeric(4, 1) USING temperature::numeric(4, 1);
