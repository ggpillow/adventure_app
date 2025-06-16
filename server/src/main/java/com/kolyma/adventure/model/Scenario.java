package com.kolyma.adventure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "scenarios")
@Data //генерирует геттеры, сеттеры
@NoArgsConstructor //пустой конструктор
@AllArgsConstructor //конструктор со всеми полями
public class Scenario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Название сценария не может быть пустым")
    private String title;

    @Column(name = "start_descr", nullable = false)
    private String startDescr;

    @Column(name = "mini_description")
    private String miniDescription;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "difficulty", nullable = false)
    @NotBlank(message = "Уровень сложности обязателен")
    private String difficulty;
}