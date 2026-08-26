-- Adds the auth tables from #43's schema redesign: users/roles/permissions
-- plus their join tables. Pure addition - no existing data to migrate, no
-- domain/usecase/UI login flow yet (out of scope for #48, see the PRD).
--
-- users.person_id has no cascade from either side: a person already exists
-- independently of any login, and deleting a user must not delete the
-- person, unlike the patients/doctors/nurses/receptionists role tables
-- which own their person's lifecycle.

CREATE TABLE public.users (
    id bigint NOT NULL,
    person_id bigint NOT NULL,
    username character varying(255) NOT NULL,
    password_hash character varying(255) NOT NULL,
    is_active boolean NOT NULL DEFAULT true,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_person_id_key UNIQUE (person_id),
    CONSTRAINT users_username_key UNIQUE (username)
);

CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_person_id_fkey FOREIGN KEY (person_id) REFERENCES public.people(id);

CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(255),
    CONSTRAINT roles_pkey PRIMARY KEY (id),
    CONSTRAINT roles_name_key UNIQUE (name)
);

-- No sequence: seeded here, only ever read by the app going forward, same
-- convention as marital_statuses.
INSERT INTO public.roles (id, name, description) VALUES
    (1, 'ADMIN', 'Full system access'),
    (2, 'DOCTOR', 'Clinical staff - doctor'),
    (3, 'NURSE', 'Clinical staff - nurse'),
    (4, 'RECEPTIONIST', 'Front-desk staff');

CREATE TABLE public.permissions (
    id bigint NOT NULL,
    code character varying(100) NOT NULL,
    description character varying(255),
    CONSTRAINT permissions_pkey PRIMARY KEY (id),
    CONSTRAINT permissions_code_key UNIQUE (code)
);

CREATE SEQUENCE public.permissions_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.role_permissions (
    role_id bigint NOT NULL,
    permission_id bigint NOT NULL,
    CONSTRAINT role_permissions_pkey PRIMARY KEY (role_id, permission_id),
    CONSTRAINT role_permissions_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id),
    CONSTRAINT role_permissions_permission_id_fkey FOREIGN KEY (permission_id) REFERENCES public.permissions(id)
);

CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id),
    CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id)
);
