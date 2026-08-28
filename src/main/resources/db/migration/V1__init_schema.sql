CREATE TABLE practicantes (
    id UUID PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    nivel INT NOT NULL,
    experiencia INT NOT NULL,
    racha INT NOT NULL,
    armonia INT NOT NULL,
    maleza INT NOT NULL
);

CREATE TABLE recordatorios (
    id UUID PRIMARY KEY,
    practicante_id UUID,
    titulo VARCHAR(255),
    completado BOOLEAN NOT NULL
);

CREATE TABLE rutinas_diarias (
    id UUID PRIMARY KEY,
    practicante_id UUID,
    titulo VARCHAR(255),
    series_totales INT NOT NULL,
    series_completadas INT NOT NULL
);

CREATE TABLE tareas_enfoque (
    id UUID PRIMARY KEY,
    practicante_id UUID,
    titulo VARCHAR(255),
    completado BOOLEAN NOT NULL
);

CREATE TABLE ciclos_enfoque (
    id UUID PRIMARY KEY,
    tarea_enfoque_id UUID,
    hora_inicio TIMESTAMP(6),
    hora_fin TIMESTAMP(6),
    estado VARCHAR(255)
);

ALTER TABLE ciclos_enfoque
    ADD CONSTRAINT fk_ciclos_tarea
    FOREIGN KEY (tarea_enfoque_id) REFERENCES tareas_enfoque(id);

CREATE TABLE planificaciones (
    id UUID PRIMARY KEY,
    practicante_id UUID,
    fecha DATE
);

CREATE TABLE planificacion_recordatorios (
    planificacion_id UUID NOT NULL,
    recordatorio_id UUID NOT NULL,
    CONSTRAINT fk_pr_planificacion FOREIGN KEY (planificacion_id) REFERENCES planificaciones(id),
    CONSTRAINT fk_pr_recordatorio FOREIGN KEY (recordatorio_id) REFERENCES recordatorios(id)
);

CREATE TABLE planificacion_tareas_enfoque (
    planificacion_id UUID NOT NULL,
    tarea_enfoque_id UUID NOT NULL,
    CONSTRAINT fk_pte_planificacion FOREIGN KEY (planificacion_id) REFERENCES planificaciones(id),
    CONSTRAINT fk_pte_tarea FOREIGN KEY (tarea_enfoque_id) REFERENCES tareas_enfoque(id)
);

CREATE TABLE planificacion_rutinas (
    planificacion_id UUID NOT NULL,
    rutina_id UUID NOT NULL,
    CONSTRAINT fk_prut_planificacion FOREIGN KEY (planificacion_id) REFERENCES planificaciones(id),
    CONSTRAINT fk_prut_rutina FOREIGN KEY (rutina_id) REFERENCES rutinas_diarias(id)
);
