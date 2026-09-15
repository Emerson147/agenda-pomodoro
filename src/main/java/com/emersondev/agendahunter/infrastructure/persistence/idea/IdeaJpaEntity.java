package com.emersondev.agendahunter.infrastructure.persistence.idea;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ideas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdeaJpaEntity {
    @Id
    private UUID id;
    private UUID practicanteId;
    private String contenido;
    private LocalDateTime fechaCaptura;
    private boolean procesada;
}
