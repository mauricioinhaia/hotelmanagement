CREATE TABLE IF NOT EXISTS hospedes (
    id        BIGSERIAL    PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    documento VARCHAR(14)  NOT NULL,
    email     VARCHAR(255),
    telefone  VARCHAR(20),

    CONSTRAINT email_unique     UNIQUE (email),
    CONSTRAINT documento_unique UNIQUE (documento)
);

COMMENT ON COLUMN hospedes.id            IS 'Identificador único do hóspede';
COMMENT ON COLUMN hospedes.nome          IS 'Nome completo do hóspede';
COMMENT ON COLUMN hospedes.documento     IS 'Número de documento (CPF/CNPJ) do hóspede';
COMMENT ON COLUMN hospedes.email         IS 'Endereço de e-mail do hóspede';
COMMENT ON COLUMN hospedes.telefone      IS 'Número de telefone do hóspede';

CREATE TABLE IF NOT EXISTS reserva (
    id                    BIGSERIAL      PRIMARY KEY,
    hospede               BIGINT         NOT NULL,
    checkIn               TIMESTAMP      NOT NULL,
    checkOut              TIMESTAMP      NOT NULL,
    estacionamento        BOOLEAN        NOT NULL DEFAULT FALSE,
    status                CHAR(1)        CHECK (status IN ('A', 'C', 'F')) NOT NULL,
    valorTotal            DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_hospede FOREIGN KEY (hospede) REFERENCES hospedes(id) ON DELETE CASCADE
);

COMMENT ON COLUMN reserva.id                    IS 'Identificador único do check-in';
COMMENT ON COLUMN reserva.hospede               IS 'Hóspede relacionado a este check-in (id da tabela hospede)';
COMMENT ON COLUMN reserva.checkIn               IS 'Data e hora de entrada do hóspede';
COMMENT ON COLUMN reserva.checkOut              IS 'Data e hora de saída do hóspede';
COMMENT ON COLUMN reserva.estacionamento        IS 'Indica se o hóspede utilizou estacionamento (true/false)';
COMMENT ON COLUMN reserva.status                IS 'Status do check-in: A - Aberto, C - Cancelado, F - Finalizado';
COMMENT ON COLUMN reserva.valorTotal            IS 'Valor total da estadia do hóspede';
