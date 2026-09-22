CREATE TABLE reflexiones_diarias (
    id UUID PRIMARY KEY,
    calendario_id UUID NOT NULL,
    pregunta VARCHAR(1000) NOT NULL,
    respuesta VARCHAR(2000) NOT NULL,
    CONSTRAINT fk_reflexion_calendario FOREIGN KEY (calendario_id) REFERENCES planificaciones(id) ON DELETE CASCADE
);
