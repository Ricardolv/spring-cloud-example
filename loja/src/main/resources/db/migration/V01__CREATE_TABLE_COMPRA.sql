
CREATE SEQUENCE hibernate_sequence
START 1 INCREMENT 1;


CREATE TABLE IF NOT EXISTS compra (
    id bigint NOT NULL,
    tempo_preparo int4 NOT NULL,
    endereco_destino VARCHAR(255),
    PRIMARY KEY (id)
);
