package com.example.adventureapp.data.model;

public class Scheme {
    private Long id;
    private Long scenarioId;
    private String imageSchemes;

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageSchemes() {
        return imageSchemes;
    }

    public Long getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
    }

    public void setImageSchemes(String imageSchemes) {
        this.imageSchemes = imageSchemes;
    }
}