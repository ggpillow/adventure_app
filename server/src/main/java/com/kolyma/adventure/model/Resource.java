package com.kolyma.adventure.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resources")
@Data //генерирует геттеры, сеттеры
@NoArgsConstructor //пустой конструктор
@AllArgsConstructor //конструктор со всеми полями
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
