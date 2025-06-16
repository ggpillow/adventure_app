package com.kolyma.adventure.controller;

import com.kolyma.adventure.service.EndingService;
import com.kolyma.adventure.model.Ending;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EndingControllerTest {

    @Mock
    private EndingService endingService;

    @InjectMocks
    private EndingController endingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(endingController).build();
    }

    @Test
    void getEndingsByScenario_ShouldReturnList() throws Exception {
        List<Ending> endings = List.of(
                new Ending(1L, "Good Ending", "Happy end", null, null),
                new Ending(2L, "Bad Ending", "Sad end", "Some question?", null)
        );

        when(endingService.getByScenarioId(1L)).thenReturn(endings);

        mockMvc.perform(get("/endings/scenario/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].titleEnding", is("Good Ending")))
                .andExpect(jsonPath("$[1].altQuestion", is("Some question?")));

        verify(endingService).getByScenarioId(1L);
    }

    @Test
    void getEndingsByScenario_ShouldReturnNotFound_WhenEmpty() throws Exception {
        when(endingService.getByScenarioId(1L)).thenReturn(List.of());

        mockMvc.perform(get("/endings/scenario/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(endingService).getByScenarioId(1L);
    }

    @Test
    void getEndingByScenarioAndTitle_ShouldReturnEnding() throws Exception {
        Ending ending = new Ending(10L, "Special Ending", "Unique end", "Alt question", null);

        when(endingService.getByScenarioIdAndTitle(1L, "Special Ending")).thenReturn(ending);

        mockMvc.perform(get("/endings/scenario/1/title")
                        .param("titleEnding", "Special Ending")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titleEnding", is("Special Ending")))
                .andExpect(jsonPath("$.altQuestion", is("Alt question")));

        verify(endingService).getByScenarioIdAndTitle(1L, "Special Ending");
    }

    @Test
    void getEndingByScenarioAndTitle_ShouldReturnNotFound_WhenException() throws Exception {
        when(endingService.getByScenarioIdAndTitle(1L, "Missing Ending"))
                .thenThrow(new RuntimeException("not found"));

        mockMvc.perform(get("/endings/scenario/1/title")
                        .param("titleEnding", "Missing Ending")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(endingService).getByScenarioIdAndTitle(1L, "Missing Ending");
    }
}
