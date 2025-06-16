package com.example.adventureapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREF_NAME = "user_settings";
    private static final String KEY_MUSIC_ENABLED = "isMusicEnabled";
    private static final String KEY_SOUND_ENABLED = "isSoundEnabled";

    // Получение SharedPreferences
    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Статические методы для чтения
    public static boolean isMusicEnabled(Context context) {
        return getPrefs(context).getBoolean(KEY_MUSIC_ENABLED, true);
    }

    public static boolean isSoundEnabled(Context context) {
        return getPrefs(context).getBoolean(KEY_SOUND_ENABLED, true);
    }

    // Статические методы для записи
    public static void setMusicEnabled(Context context, boolean enabled) {
        getPrefs(context).edit().putBoolean(KEY_MUSIC_ENABLED, enabled).apply();
    }

    public static void setSoundEnabled(Context context, boolean enabled) {
        getPrefs(context).edit().putBoolean(KEY_SOUND_ENABLED, enabled).apply();
    }

    public static void resetToDefault(Context context) {
        getPrefs(context).edit()
                .putBoolean(KEY_MUSIC_ENABLED, true)
                .putBoolean(KEY_SOUND_ENABLED, true)
                .apply();
    }
}