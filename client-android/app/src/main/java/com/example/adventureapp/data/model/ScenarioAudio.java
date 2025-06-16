package com.example.adventureapp.data.model;

public class ScenarioAudio {
    private Long id;
    private Long scenarioId;
    private String audioUrl;

    public Long getId() { return id; }
    public Long getScenarioId() { return scenarioId; }
    public String getAudioUrl() { return audioUrl; }

    public void setId(Long id) { this.id = id; }
    public void setScenarioId(Long scenarioId) { this.scenarioId = scenarioId; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }
}