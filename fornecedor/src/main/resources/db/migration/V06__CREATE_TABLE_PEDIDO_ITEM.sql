CREATE TABLE pedido_item (
	id bigserial NOT NULL,
	quantidade int4 NULL,
	produto_id int8 NULL,
	pedido_id int8 NULL,
	CONSTRAINT pedido_item_pkey PRIMARY KEY (id)
);

ALTER TABLE pedido_item ADD CONSTRAINT fk8eyfr31j751fjws2y012awmpg FOREIGN KEY (produto_id) REFERENCES produto(id);
ALTER TABLE pedido_item ADD CONSTRAINT fkeyouxfvoi291lpo5168e6wpej FOREIGN KEY (pedido_id) REFERENCES pedido(id);