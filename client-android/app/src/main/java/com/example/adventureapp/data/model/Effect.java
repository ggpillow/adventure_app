package com.example.adventureapp.data.model;

public class Effect {
    private Long id;
    private String name;
    private String description;
    private String effectType;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEffectType() {
        return effectType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }
}