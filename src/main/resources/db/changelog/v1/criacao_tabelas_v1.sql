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

CREATE TABLE IF NOT EXISTS reservas (
    id                    BIGSERIAL PRIMARY KEY,
    hospede               BIGINT    NOT NULL,
    checkin               TIMESTAMP,
    checkout              TIMESTAMP,
    estacionamento        BOOLEAN   NOT NULL DEFAULT FALSE,
    status                CHAR(1)   CHECK (status IN ('A', 'C', 'F')) NOT NULL,
    valortotal            DECIMAL(10, 2),

    CONSTRAINT fk_hospede FOREIGN KEY (hospede) REFERENCES hospedes(id) ON DELETE CASCADE
);

COMMENT ON COLUMN reservas.id                    IS 'Identificador único do check-in';
COMMENT ON COLUMN reservas.hospede               IS 'Hóspede relacionado a este check-in (id da tabela hospede)';
COMMENT ON COLUMN reservas.checkin               IS 'Data e hora de entrada do hóspede';
COMMENT ON COLUMN reservas.checkout              IS 'Data e hora de saída do hóspede';
COMMENT ON COLUMN reservas.estacionamento        IS 'Indica se o hóspede utilizou estacionamento (true/false)';
COMMENT ON COLUMN reservas.status                IS 'Status do check-in: A - Aberto, C - Cancelado, F - Finalizado';
COMMENT ON COLUMN reservas.valortotal            IS 'Valor total da estadia do hóspede';
