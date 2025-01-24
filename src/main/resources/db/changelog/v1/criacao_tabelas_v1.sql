CREATE TABLE IF NOT EXISTS hospede (
    id       BIGSERIAL    PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),

    CONSTRAINT email_unique UNIQUE (email)
);

COMMENT ON COLUMN hospede.id       IS 'Identificador único do hóspede';
COMMENT ON COLUMN hospede.nome     IS 'Nome completo do hóspede';
COMMENT ON COLUMN hospede.email    IS 'Endereço de e-mail do hóspede';
COMMENT ON COLUMN hospede.telefone IS 'Número de telefone do hóspede';

CREATE TABLE IF NOT EXISTS checkin (
    id                    BIGSERIAL      PRIMARY KEY,
    hospede               BIGINT         NOT NULL,
    dataEntrada           TIMESTAMP      NOT NULL,
    dataSaida             TIMESTAMP      NOT NULL,
    estacionamento        BOOLEAN        NOT NULL DEFAULT FALSE,
    status                CHAR(1)        CHECK (status IN ('A', 'C', 'F')) NOT NULL,
    valorTotal            DECIMAL(10, 2) NOT NULL,
    valorUltimaHospedagem DECIMAL(10, 2),

    CONSTRAINT fk_hospede FOREIGN KEY (hospede) REFERENCES hospede(id) ON DELETE CASCADE
);

COMMENT ON COLUMN checkin.id                    IS 'Identificador único do check-in';
COMMENT ON COLUMN checkin.hospede               IS 'Hóspede relacionado a este check-in (id da tabela hospede)';
COMMENT ON COLUMN checkin.dataEntrada           IS 'Data e hora de entrada do hóspede';
COMMENT ON COLUMN checkin.dataSaida             IS 'Data e hora de saída do hóspede';
COMMENT ON COLUMN checkin.estacionamento        IS 'Indica se o hóspede utilizou estacionamento (true/false)';
COMMENT ON COLUMN checkin.status                IS 'Status do check-in: A - Aberto, C - Cancelado, F - Finalizado';
COMMENT ON COLUMN checkin.valorTotal            IS 'Valor total da estadia do hóspede';
COMMENT ON COLUMN checkin.valorUltimaHospedagem IS 'Valor cobrado na última hospedagem';
