CREATE TABLE cliente (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    telefone VARCHAR(100),
    senha VARCHAR(255)
);

CREATE TABLE barbeiro (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    telefone VARCHAR(100)
);

CREATE TABLE servico (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    valor DECIMAL(10, 2)
);

CREATE TABLE agendamento (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    data_hora TIMESTAMP,
    cliente_id BIGINT,
    barbeiro_id BIGINT,
    servico_id BIGINT,
    status VARCHAR(20),

    CONSTRAINT fk_agendamento_cliente
        FOREIGN KEY (cliente_id) REFERENCES cliente(id),

    CONSTRAINT fk_agendamento_barbeiro
        FOREIGN KEY (barbeiro_id) REFERENCES barbeiro(id),

    CONSTRAINT fk_agendamento_servico
        FOREIGN KEY (servico_id) REFERENCES servico(id)
);