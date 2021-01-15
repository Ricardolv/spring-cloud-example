CREATE TABLE IF NOT EXISTS compra (
    id bigserial NOT NULL,
    pedido_id bigint,
    tempo_preparo int4,
    endereco_destino VARCHAR(255),
    voucher bigint,
	data_entrega timestamp,
	state varchar(50) NOT NULL,
    PRIMARY KEY (id)
);
