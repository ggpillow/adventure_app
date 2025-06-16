package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.ScenarioDTO;
import com.kolyma.adventure.model.Scenario;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

public class ScenarioMapper {

    public static ScenarioDTO toDTO(Scenario scenario){
        if (scenario == null) return null;
        return new ScenarioDTO(
                scenario.getId(),
                scenario.getTitle(),
                scenario.getStartDescr(),
                scenario.getMiniDescription(),
                scenario.getImageURL(),
                scenario.getDifficulty()
        );
    }

    public static Scenario toEntity(ScenarioDTO dto) {
        if (dto == null) return null;
        Scenario scenario = new Scenario();
        scenario.setId(dto.getId());
        scenario.setTitle(dto.getTitle());
        scenario.setStartDescr(dto.getStartDescr());
        scenario.setMiniDescription(dto.getMiniDescription());
        scenario.setImageURL(dto.getImageURL());
        scenario.setDifficulty(dto.getDifficulty());
        return scenario;
    }
}
