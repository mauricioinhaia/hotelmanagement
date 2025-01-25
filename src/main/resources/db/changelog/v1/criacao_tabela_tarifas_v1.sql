CREATE TABLE IF NOT EXISTS tarifa (
    id              BIGSERIAL PRIMARY KEY,
    descricaotarifa VARCHAR(40) NOT NULL,
    fimdesemana     BOOLEAN NOT NULL,
    tiposervico     CHAR(1) CHECK (tiposervico IN ('H', 'E', 'J')) NOT NULL,
    valor           DECIMAL(10, 2) NOT NULL
);

COMMENT ON COLUMN tarifa.descricaotarifa IS 'Descrição da tarifa (exemplo: Hospedagem, Estacionamento)';
COMMENT ON COLUMN tarifa.fimdesemana     IS 'TRUE para tarifa de final de semana, FALSE para tarifa de semana (segunda a sexta)';
COMMENT ON COLUMN tarifa.tiposervico     IS 'H = Hospedagem, E = Estacionamento';
COMMENT ON COLUMN tarifa.valor           IS 'Valor da tarifa, seja diária ou estacionamento, conforme o período';

INSERT INTO tarifa (descricaotarifa, fimdesemana, tiposervico, valor)
VALUES
    ('Hospedagem dia de Semana',     FALSE, 'H', 120.00),
    ('Hospedagem fim de Semana',     TRUE,  'H', 150.00),
    ('Estacionamento dia de Semana', FALSE, 'E', 15.00),
    ('Estacionamento fim de Semana', TRUE,  'E', 20.00);
