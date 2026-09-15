CREATE TABLE ideas (
    id UUID PRIMARY KEY,
    practicante_id UUID NOT NULL,
    contenido TEXT NOT NULL,
    fecha_captura TIMESTAMP NOT NULL,
    procesada BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_ideas_practicante FOREIGN KEY (practicante_id) REFERENCES practicantes(id) ON DELETE CASCADE
);
