package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.EndingDTO;
import com.kolyma.adventure.model.Ending;
import com.kolyma.adventure.model.Scenario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndingMapper {

    // Преобразование из сущности Ending в DTO
    public static EndingDTO toDTO(Ending ending) {
        if (ending == null) return null;

        return new EndingDTO(
                ending.getId(),
                ending.getTitleEnding(),
                ending.getEndDescr(),
                ending.getAltQuestion(), // корректно
                (ending.getScenario() != null) ? ending.getScenario().getId() : null
        );
    }

    // Преобразование из DTO в сущность Ending (без сценария)
    public static Ending toEntity(EndingDTO dto) {
        if (dto == null) return null;

        Ending ending = new Ending();
        ending.setId(dto.getId());
        ending.setTitleEnding(dto.getTitleEnding());
        ending.setEndDescr(dto.getEndDescr());
        ending.setAltQuestion(dto.getAltQuestion()); // 💡 вот это было пропущено!
        return ending;
    }

    // Преобразование из DTO в сущность Ending с установкой Scenario
    public static Ending toEntity(EndingDTO dto, Scenario scenario) {
        if (dto == null) return null;

        Ending ending = new Ending();
        ending.setId(dto.getId());
        ending.setTitleEnding(dto.getTitleEnding());
        ending.setEndDescr(dto.getEndDescr());
        ending.setAltQuestion(dto.getAltQuestion()); // 💡 добавлено и тут
        ending.setScenario(scenario);
        return ending;
    }
}
