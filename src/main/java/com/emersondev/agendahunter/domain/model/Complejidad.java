package com.emersondev.agendahunter.domain.model;

import lombok.Getter;

@Getter
public enum Complejidad {
    S(500),
    A(250),
    B(100),
    C(50),
    D(20),
    E(10);

    private final int experienciaOtorgada;

    Complejidad(int experienciaOtorgada) {
        this.experienciaOtorgada = experienciaOtorgada;
    }
}
