package com.kolyma.adventure.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_combinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCombination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_resource_id", nullable = false)
    private Resource firstResource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_resource_id", nullable = false)
    private Resource secondResource;

    @Column(name = "result_item", nullable = false)
    private String resultItem;

    @Column(name = "image_items")
    private String imageItems;
}