CREATE TABLE produto (
	id bigserial NOT NULL,
	descricao varchar(255) NULL,
	estado varchar(255) NULL,
	nome varchar(255) NULL,
	preco numeric(19,2) NULL,
	CONSTRAINT produto_pkey PRIMARY KEY (id)
);