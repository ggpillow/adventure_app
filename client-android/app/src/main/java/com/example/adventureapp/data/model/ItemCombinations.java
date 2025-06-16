package com.example.adventureapp.data.model;

public class ItemCombinations {
    private Long id;
    private Resource firstResource;
    private Resource secondResource;
    private String result;
    private String imageItems;

    public Long getId() { return id; }
    public Resource getFirstResource() { return firstResource; }
    public Resource getSecondResource() { return secondResource; }
    public String getResult() { return result; }
    public String getImageItems() {
        return imageItems;
    }

    public void setId(Long id) { this.id = id; }
    public void setFirstResource(Resource firstResource) { this.firstResource = firstResource; }
    public void setSecondResource(Resource secondResource) { this.secondResource = secondResource; }
    public void setResult(String result) { this.result = result; }
    public void setImageItems(String imageItems) {
        this.imageItems = imageItems;
    }
}