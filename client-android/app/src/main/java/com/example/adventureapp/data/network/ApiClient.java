package com.example.adventureapp.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Клиент для подключения к API-серверу с помощью Retrofit
public class ApiClient {

    // Экземпляр Retrofit
    private static Retrofit retrofit = null;

    // Метод для получения клиента Retrofit
    public static Retrofit getClient() {
        // Инициализация только один раз (паттерн Singleton)
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConfig.BASE_URL) // Базовый URL сервера
                    .addConverterFactory(GsonConverterFactory.create()) // Конвертер JSON -> Java
                    .build();
        }
        return retrofit;
    }
}