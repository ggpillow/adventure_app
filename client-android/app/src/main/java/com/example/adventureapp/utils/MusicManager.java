package com.example.adventureapp.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.adventureapp.R;

public class MusicManager {

    private static MediaPlayer mediaPlayer;      // для фоновой музыки
    private static MediaPlayer voicePlayer;      // для озвучки

    // ▶️ Запуск фоновой музыки
    public static void start(Context context) {
        if (mediaPlayer == null && PreferenceManager.isMusicEnabled(context)) {
            mediaPlayer = MediaPlayer.create(context, R.raw.menu_music);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.start();
        }
    }

    // ⏹️ Плавная остановка фоновой музыки
    public static void stopWithFadeOut() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            new Thread(() -> {
                float volume = 1f;
                while (volume > 0f) {
                    volume -= 0.1f;
                    synchronized (MusicManager.class) {
                        if (mediaPlayer != null) {
                            mediaPlayer.setVolume(volume, volume);
                        }
                    }
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException ignored) {}
                }
                synchronized (MusicManager.class) {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            }).start();
        }
    }

    // ❌ Жёсткая остановка музыки
    public static void forceStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // ▶️ Озвучка (voicePlayer)
    public static void playVoice(Context context, String audioUrl) {
        stopVoice(); // останавливаем всё, что могло остаться

        if (audioUrl == null || audioUrl.isEmpty()) {
            Log.e("VOICE", "audioUrl пустой");
            return;
        }

        // Убираем двойной слэш, если начинается с /
        if (audioUrl.startsWith("/")) {
            audioUrl = audioUrl.substring(1);
        }

        try {
            Log.d("VOICE", "Попытка проиграть аудио: " + audioUrl);

            voicePlayer = new MediaPlayer();
            voicePlayer.setDataSource(audioUrl);
            voicePlayer.setOnPreparedListener(mp -> {
                Log.d("VOICE", "Аудио подготовлено. Запускаю...");
                mp.start();
            });
            voicePlayer.setOnErrorListener((mp, what, extra) -> {
                Log.e("VOICE", "Ошибка воспроизведения: what=" + what + ", extra=" + extra);
                return true; // ошибка обработана
            });
            voicePlayer.prepareAsync();

        } catch (Exception e) {
            Log.e("VOICE", "Исключение при попытке проигрывания", e);
        }
    }

    // ❌ Остановить озвучку
    public static void stopVoice() {
        if (voicePlayer != null) {
            if (voicePlayer.isPlaying()) {
                voicePlayer.stop();
            }
            voicePlayer.release();
            voicePlayer = null;
        }
    }

    public static boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public static boolean isVoicePlaying() {
        return voicePlayer != null && voicePlayer.isPlaying();
    }
}