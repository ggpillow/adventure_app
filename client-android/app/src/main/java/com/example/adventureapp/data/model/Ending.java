package com.example.adventureapp.data.model;

public class Ending {
    private Long id;
    private String titleEnding;
    private String endDescr;
    private String altQuestion; // Новое поле
    private Long scenarioId;

    public Long getId() { return id; }
    public String getTitleEnding() { return titleEnding; }
    public String getEndDescr() { return endDescr; }
    public String getAltQuestion() { return altQuestion; } // Геттер
    public Long getScenarioId() { return scenarioId; }

    public void setId(Long id) { this.id = id; }
    public void setTitleEnding(String titleEnding) { this.titleEnding = titleEnding; }
    public void setEndDescr(String endDescr) { this.endDescr = endDescr; }
    public void setAltQuestion(String altQuestion) { this.altQuestion = altQuestion; } // Сеттер
    public void setScenarioId(Long scenarioId) { this.scenarioId = scenarioId; }
}
