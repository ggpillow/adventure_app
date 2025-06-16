package com.kolyma.adventure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "endings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title_ending")
    @NotBlank(message = "Заголовок концовки обязателен")
    private String titleEnding;

    @Column(name = "end_descr")
    @NotBlank(message = "Описание концовки обязательно")
    private String endDescr;

    @Column(name = "alt_question")
    private String altQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;
}
