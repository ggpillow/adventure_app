package com.example.adventureapp.data.network;

import com.example.adventureapp.data.model.Paragraph;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ParagraphApi {

    // Получить все абзацы
    @GET("paragraphs")
    Call<List<Paragraph>> getAllParagraphs();

    // Получить абзац по идентификатору
    @GET("paragraphs/{id}")
    Call<Paragraph> getParagraphById(@Path("id") Long id);

    // Получить абзац по номеру
    @GET("paragraphs/number/{number}")
    Call<Paragraph> getParagraphByNumber(@Path("number") int number);
}