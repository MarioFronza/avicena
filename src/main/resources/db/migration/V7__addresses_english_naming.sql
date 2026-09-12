-- Renames endereco -> addresses per the #43 schema redesign. Unlike the
-- person tables (medico/paciente/...), endereco is still live-referenced
-- by people.address_id, so this preserves data via ALTER RENAME rather
-- than drop-and-recreate.

ALTER TABLE public.endereco RENAME TO addresses;
ALTER TABLE public.addresses RENAME CONSTRAINT endereco_pkey TO addresses_pkey;
ALTER TABLE public.addresses RENAME COLUMN codigo_endereco TO id;
ALTER TABLE public.addresses RENAME COLUMN numero TO number;
ALTER TABLE public.addresses RENAME COLUMN complento TO complement;
ALTER TABLE public.addresses RENAME COLUMN cep TO zip_code;
ALTER TABLE public.addresses RENAME COLUMN rua TO street;
ALTER TABLE public.addresses RENAME COLUMN bairro TO neighborhood;
ALTER TABLE public.addresses RENAME COLUMN cidade TO city;
ALTER TABLE public.addresses ADD COLUMN created_at timestamp NOT NULL DEFAULT now();
ALTER TABLE public.addresses ADD COLUMN updated_at timestamp NOT NULL DEFAULT now();

ALTER SEQUENCE public.endereco_seq RENAME TO addresses_seq;
