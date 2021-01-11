CREATE TABLE IF NOT EXISTS compra (
    id bigserial NOT NULL,
    tempo_preparo int4 NOT NULL,
    endereco_destino VARCHAR(255),
    PRIMARY KEY (id)
);
