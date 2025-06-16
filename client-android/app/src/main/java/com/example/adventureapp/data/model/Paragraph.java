package com.example.adventureapp.data.model;

public class Paragraph {
    private Long id;
    private int paragraphNumber;
    private String paragraphDescr;
    private Long effectId; // Оставим, если где-то используется отдельно
    private Effect effect; // Новый объект

    public Paragraph(Long id, int paragraphNumber, String paragraphDescr, Long effectId, Effect effect) {
        this.id = id;
        this.paragraphNumber = paragraphNumber;
        this.paragraphDescr = paragraphDescr;
        this.effectId = effectId;
        this.effect = effect;
    }

    public Long getId() {
        return id;
    }

    public int getParagraphNumber() {
        return paragraphNumber;
    }

    public String getParagraphDescr() {
        return paragraphDescr;
    }

    public Long getEffectId() {
        return effectId;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParagraphNumber(int paragraphNumber) {
        this.paragraphNumber = paragraphNumber;
    }

    public void setParagraphDescr(String paragraphDescr) {
        this.paragraphDescr = paragraphDescr;
    }

    public void setEffectId(Long effectId) {
        this.effectId = effectId;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
}