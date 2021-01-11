CREATE TABLE IF NOT EXISTS info_fornecedor (
    id bigserial NOT NULL,
    nome VARCHAR(50) NOT NULL,
    endereco VARCHAR(255),
    estado VARCHAR(255),
    PRIMARY KEY (id)
);
