package com.example.adventureapp.data.model;

public class Scenario {
    private Long id;
    private String title;
    private String startDescr;
    private String miniDescription;
    private String imageURL;
    private String difficulty;
    private String audioUrl;

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getStartDescr() { return startDescr; }
    public String getMiniDescription() { return miniDescription; }
    public String getImageURL() { return imageURL; }
    public String getDifficulty() { return difficulty; }
    public String getAudioUrl() { return audioUrl; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setStartDescr(String startDescr) { this.startDescr = startDescr; }
    public void setMiniDescription(String miniDescription) { this.miniDescription = miniDescription; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}