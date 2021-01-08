
CREATE SEQUENCE hibernate_sequence
START 1 INCREMENT 1;


CREATE TABLE IF NOT EXISTS info_fornecedor (
    id bigint NOT NULL,
    nome VARCHAR(50) NOT NULL,
    endereco VARCHAR(255),
    estado VARCHAR(255),
    PRIMARY KEY (id)
);
