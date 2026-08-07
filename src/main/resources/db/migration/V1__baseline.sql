-- Baseline migration: reproduces the schema Hibernate's
-- schema-generation.database.action=create currently generates from the
-- @Entity classes, verbatim (captured via pg_dump against a live Hibernate
-- run). Zero behavior change: this migration exists so Flyway can take over
-- schema ownership from Hibernate's auto-DDL, not to redesign anything.

CREATE TABLE public.atendente (
    carga_horaria integer NOT NULL,
    estado_civil smallint,
    hora_extra integer NOT NULL,
    numero_carteira_de_trabalho integer NOT NULL,
    salario real NOT NULL,
    codigo bigint NOT NULL,
    codigo_endereco bigint,
    cpf character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    CONSTRAINT atendente_estado_civil_check CHECK (((estado_civil >= 0) AND (estado_civil <= 4)))
);

CREATE SEQUENCE public.atendente_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.consulta (
    estado_paciente smallint,
    codigo bigint NOT NULL,
    codigo_enfermeiro bigint,
    codigo_medico bigint,
    codigo_paciente bigint,
    data character varying(255),
    hora character varying(255),
    sintomas character varying(255),
    CONSTRAINT consulta_estado_paciente_check CHECK (((estado_paciente >= 0) AND (estado_paciente <= 3)))
);

CREATE SEQUENCE public.consulta_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.diagnostico_final (
    codigo bigint NOT NULL,
    descricao character varying(255),
    doenca character varying(255),
    exame character varying(255),
    remedios character varying(255)
);

CREATE SEQUENCE public.diagnostico_final_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.diagnostico_primario (
    altura integer,
    peso integer,
    pressao real,
    temperatura integer,
    codigo bigint NOT NULL,
    id_consulta bigint,
    id_diagnostico_final bigint,
    historico_remedio character varying(255)
);

CREATE SEQUENCE public.diagnostico_primario_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.endereco (
    numero integer,
    codigo_endereco bigint NOT NULL,
    bairro character varying(255),
    cep character varying(255),
    cidade character varying(255),
    complento character varying(255),
    rua character varying(255)
);

CREATE SEQUENCE public.endereco_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.enfermeiro (
    estado_civil smallint,
    hr_cursadas integer NOT NULL,
    codigo bigint NOT NULL,
    codigo_endereco bigint,
    cpf character varying(255) NOT NULL,
    formacao character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    CONSTRAINT enfermeiro_estado_civil_check CHECK (((estado_civil >= 0) AND (estado_civil <= 4)))
);

CREATE SEQUENCE public.enfermeiro_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.medico (
    estado_civil smallint,
    codigo bigint NOT NULL,
    codigo_endereco bigint,
    cpf character varying(255) NOT NULL,
    crm character varying(255),
    especializacao character varying(255),
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    CONSTRAINT medico_estado_civil_check CHECK (((estado_civil >= 0) AND (estado_civil <= 4)))
);

CREATE SEQUENCE public.medico_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.paciente (
    estado_civil smallint,
    codigo bigint NOT NULL,
    codigo_endereco bigint,
    cpf character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    CONSTRAINT paciente_estado_civil_check CHECK (((estado_civil >= 0) AND (estado_civil <= 4)))
);

CREATE SEQUENCE public.paciente_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.atendente
    ADD CONSTRAINT atendente_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.consulta
    ADD CONSTRAINT consulta_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.diagnostico_final
    ADD CONSTRAINT diagnostico_final_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.diagnostico_primario
    ADD CONSTRAINT diagnostico_primario_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (codigo_endereco);

ALTER TABLE ONLY public.enfermeiro
    ADD CONSTRAINT enfermeiro_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.medico
    ADD CONSTRAINT medico_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.diagnostico_primario
    ADD CONSTRAINT fk10okludahu1xd8m6el94os205 FOREIGN KEY (id_diagnostico_final) REFERENCES public.diagnostico_final(codigo);

ALTER TABLE ONLY public.medico
    ADD CONSTRAINT fk1cqeljth0djg879pg8j4jo7m7 FOREIGN KEY (codigo_endereco) REFERENCES public.endereco(codigo_endereco);

ALTER TABLE ONLY public.consulta
    ADD CONSTRAINT fk1y8rhasa863ls7qf3b7hx2snt FOREIGN KEY (codigo_paciente) REFERENCES public.paciente(codigo);

ALTER TABLE ONLY public.consulta
    ADD CONSTRAINT fkb2ko33vr2wi0gapv2pfk0gfav FOREIGN KEY (codigo_enfermeiro) REFERENCES public.enfermeiro(codigo);

ALTER TABLE ONLY public.diagnostico_primario
    ADD CONSTRAINT fkb4yisa8fa3uy4pqm961r6q5bd FOREIGN KEY (id_consulta) REFERENCES public.consulta(codigo);

ALTER TABLE ONLY public.consulta
    ADD CONSTRAINT fkgk9ps7v5htglw6jmgkl3n60e1 FOREIGN KEY (codigo_medico) REFERENCES public.medico(codigo);

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT fkmy6ptg6ufjkkge45uulfa23rb FOREIGN KEY (codigo_endereco) REFERENCES public.endereco(codigo_endereco);

ALTER TABLE ONLY public.enfermeiro
    ADD CONSTRAINT fkpg7vx1f8htw3f9bginqjre9kg FOREIGN KEY (codigo_endereco) REFERENCES public.endereco(codigo_endereco);

ALTER TABLE ONLY public.atendente
    ADD CONSTRAINT fksu818jlch59spwpswjv2qy5yi FOREIGN KEY (codigo_endereco) REFERENCES public.endereco(codigo_endereco);
