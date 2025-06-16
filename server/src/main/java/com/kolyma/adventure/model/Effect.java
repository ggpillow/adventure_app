package com.kolyma.adventure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "effects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Effect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя эффекта обязательно")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "effect_type", nullable = false)
    private String effectType; // positive, negative, choice
}