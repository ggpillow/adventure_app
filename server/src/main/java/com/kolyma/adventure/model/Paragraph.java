package com.kolyma.adventure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paragraphs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paragraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Номер абзаца должен быть больше 0")
    @Column(name = "paragraph_number", nullable = false)
    private int paragraphNumber;

    @NotBlank(message = "Текст абзаца обязателен")
    @Column(name = "paragraph_descr", nullable = false)
    private String paragraphDescr;

    @ManyToOne
    @JoinColumn(name = "effect_id")
    private Effect effect;
}
