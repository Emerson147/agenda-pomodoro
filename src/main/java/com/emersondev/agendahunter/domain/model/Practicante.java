package com.emersondev.agendahunter.domain.model;

import com.emersondev.agendahunter.domain.exception.DomainException;
import lombok.Getter;
import java.util.UUID;

@Getter
public class Practicante {

    private UUID id;
    private String nombre;
    private int nivel;
    private int experiencia;
    private int racha;
    private int armonia;
    private String email;
    private String password;
    private int maleza;
    private String zonaHoraria;

    // Constructor para un Practicante nuevo
    public Practicante(UUID id, String nombre, String email, String password) {
        this.id = id != null ? id : UUID.randomUUID();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.nivel = 1;
        this.experiencia = 0;
        this.racha = 0;
        this.armonia = 0;
        this.maleza = 0;
        this.zonaHoraria = "America/Lima";
    }

    // Constructor para rehidratar desde la base de datos
    public Practicante(UUID id, String nombre, String email, String password, int nivel, int experiencia, int racha, int armonia, int maleza, String zonaHoraria) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.racha = racha;
        this.armonia = armonia;
        this.maleza = maleza;
        this.zonaHoraria = zonaHoraria;
    }

    // Regla de Negocio: Subir de nivel como en un RPG
    public void ganarExperiencia(int expGanada) {
        if (expGanada <= 0) return;
        this.experiencia += expGanada;
        calcularNivel();
    }

    private void calcularNivel() {
        // Fórmula RPG: Cada nivel cuesta más (Nivel * 100)
        int expNecesaria = this.nivel * 100; 
        if (this.experiencia >= expNecesaria) {
            this.nivel++;
            this.experiencia -= expNecesaria;
            // Llamada recursiva por si gana mucha exp y sube varios niveles a la vez
            calcularNivel(); 
        }
    }

    public void incrementarRacha() {
        this.racha++;
    }

    public void romperRacha() {
        this.racha = 0;
    }

    public void ganarArmonia(int puntos) {
        if (puntos > 0) {
            this.armonia += puntos;
        }
    }

    public void agregarMaleza() {
        this.maleza++;
        this.racha = 0;
    }

    public void limpiarMaleza() {
        if (this.maleza > 0) {
            if (this.armonia >= 50) {
                this.armonia -= 50;
                this.maleza--;
            } else {
                throw new DomainException("Armonía insuficiente para limpiar la maleza.");
            }
        }
    }
}
