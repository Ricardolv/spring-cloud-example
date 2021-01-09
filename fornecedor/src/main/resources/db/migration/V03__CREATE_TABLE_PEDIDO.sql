CREATE TABLE pedido (
	id bigserial NOT NULL,
	status varchar(255) NULL,
	tempo_de_preparo int4 NULL,
	CONSTRAINT pedido_pkey PRIMARY KEY (id)
);