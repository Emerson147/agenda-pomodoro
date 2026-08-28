ALTER TABLE recordatorios
    ADD COLUMN fase_dia VARCHAR(50),
    ADD COLUMN hora_programada VARCHAR(50);

ALTER TABLE tareas_enfoque
    ADD COLUMN fase_dia VARCHAR(50),
    ADD COLUMN hora_programada VARCHAR(50);

ALTER TABLE rutinas_diarias
    ADD COLUMN fase_dia VARCHAR(50),
    ADD COLUMN hora_programada VARCHAR(50);
