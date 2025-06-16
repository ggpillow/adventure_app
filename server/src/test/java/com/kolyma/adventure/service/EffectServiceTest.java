package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Effect;
import com.kolyma.adventure.repository.EffectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EffectServiceTest {

    private EffectRepository effectRepository;
    private EffectService effectService;

    @BeforeEach
    @DisplayName("Инициализация моков и сервиса перед каждым тестом")
    void setUp() {
        effectRepository = Mockito.mock(EffectRepository.class);
        effectService = new EffectService(effectRepository);
    }

    @Test
    @DisplayName("Получение всех эффектов — возвращает список")
    void testGetAllEffects() {
        // given
        List<Effect> mockEffects = Arrays.asList(
                new Effect(1L, "Сила", "Увеличивает силу", "positive"),
                new Effect(2L, "Слабость", "Уменьшает силу", "negative")
        );
        when(effectRepository.findAll()).thenReturn(mockEffects);

        // when
        List<Effect> result = effectService.getAll();

        // then
        assertEquals(2, result.size());
        assertEquals("Сила", result.get(0).getName());
        verify(effectRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получение эффекта по ID — эффект найден")
    void testGetEffectById_Found() {
        // given
        Effect effect = new Effect(1L, "Мудрость", "Увеличивает интеллект", "positive");
        when(effectRepository.findById(1L)).thenReturn(Optional.of(effect));

        // when
        Optional<Effect> result = effectService.getById(1L);

        // then
        assertTrue(result.isPresent());
        assertEquals("Мудрость", result.get().getName());
        verify(effectRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Получение эффекта по ID — эффект не найден")
    void testGetEffectById_NotFound() {
        // given
        when(effectRepository.findById(99L)).thenReturn(Optional.empty());

        // when
        Optional<Effect> result = effectService.getById(99L);

        // then
        assertFalse(result.isPresent());
        verify(effectRepository, times(1)).findById(99L);
    }
}