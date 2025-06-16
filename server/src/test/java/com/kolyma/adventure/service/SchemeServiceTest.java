package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Scheme;
import com.kolyma.adventure.repository.SchemeRepository;
import com.kolyma.adventure.service.SchemeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchemeServiceTest {

    @Mock
    private SchemeRepository schemeRepository;

    @InjectMocks
    private SchemeService schemeService;

    @Test
    void testGetByScenarioIdReturnsSchemes() {
        Long scenarioId = 1L;
        List<Scheme> schemes = List.of(new Scheme(1L, null, "path1"), new Scheme(2L, null, "path2"));
        when(schemeRepository.findByScenario_Id(scenarioId)).thenReturn(schemes);

        List<Scheme> result = schemeService.getByScenarioId(scenarioId);

        assertEquals(2, result.size());
        verify(schemeRepository).findByScenario_Id(scenarioId);
    }

    @Test
    void testGetByScenarioIdReturnsEmptyList() {
        Long scenarioId = 2L;
        when(schemeRepository.findByScenario_Id(scenarioId)).thenReturn(List.of());

        List<Scheme> result = schemeService.getByScenarioId(scenarioId);

        assertTrue(result.isEmpty());
        verify(schemeRepository).findByScenario_Id(scenarioId);
    }
}
