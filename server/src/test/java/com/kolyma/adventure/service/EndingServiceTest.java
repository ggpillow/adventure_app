package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Ending;
import com.kolyma.adventure.model.Scenario;
import com.kolyma.adventure.repository.EndingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EndingServiceTest {

    @Mock
    private EndingRepository endingRepository;

    @InjectMocks
    private EndingService endingService;

    private Scenario scenario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scenario = new Scenario();
        scenario.setId(1L);
    }

    @Test
    void getByScenarioId_ShouldReturnListOfEndings() {
        List<Ending> mockEndings = List.of(
                new Ending(1L, "Good Ending", "Happy end", null, scenario),
                new Ending(2L, "Bad Ending", "Sad end", null, scenario)
        );
        when(endingRepository.findByScenario_Id(1L)).thenReturn(mockEndings);

        List<Ending> result = endingService.getByScenarioId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Good Ending", result.get(0).getTitleEnding());
        verify(endingRepository).findByScenario_Id(1L);
    }

    @Test
    void getByScenarioIdAndTitle_ShouldReturnEnding_WhenExists() {
        Ending ending = new Ending(10L, "Special Ending", "Unique end", null, scenario);
        when(endingRepository.findByScenario_IdAndTitleEnding(1L, "Special Ending")).thenReturn(ending);

        Ending result = endingService.getByScenarioIdAndTitle(1L, "Special Ending");

        assertNotNull(result);
        assertEquals("Special Ending", result.getTitleEnding());
        verify(endingRepository).findByScenario_IdAndTitleEnding(1L, "Special Ending");
    }

    @Test
    void getByScenarioIdAndTitle_ShouldThrowException_WhenNotFound() {
        when(endingRepository.findByScenario_IdAndTitleEnding(1L, "Nonexistent Ending")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                endingService.getByScenarioIdAndTitle(1L, "Nonexistent Ending")
        );

        assertTrue(exception.getMessage().contains("Концовка с названием 'Nonexistent Ending' для сценария 1 не найдена."));
        verify(endingRepository).findByScenario_IdAndTitleEnding(1L, "Nonexistent Ending");
    }
}
