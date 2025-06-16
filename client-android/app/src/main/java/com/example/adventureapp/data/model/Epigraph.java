package com.example.adventureapp.data.model;

public class Epigraph {
    private Long id;
    private String quote;
    private String author;  // 👈 добавь это

    public Long getId() { return id; }
    public String getQuote() { return quote; }
    public String getAuthor() { return author; }  // 👈 геттер

    public void setId(Long id) { this.id = id; }
    public void setQuote(String quote) { this.quote = quote; }
    public void setAuthor(String author) { this.author = author; }  // 👈 сеттер
}