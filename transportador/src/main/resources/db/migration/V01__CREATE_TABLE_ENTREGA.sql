CREATE TABLE entrega (
	id bigserial NOT NULL,
	data_entrega date NULL,
	endereco_destino varchar(255) NULL,
	endereco_origem varchar(255) NULL,
	pedido_id int8 NULL,
	previsao_entrega date NULL,
	CONSTRAINT entrega_pkey PRIMARY KEY (id)
);