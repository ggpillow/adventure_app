package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.EpigraphDTO;
import com.kolyma.adventure.model.Epigraph;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EpigraphMapper {

    public static EpigraphDTO toDTO(Epigraph epigraph) {
        if (epigraph == null) return null;
        return new EpigraphDTO(
                epigraph.getId(),
                epigraph.getQuote(),
                epigraph.getAuthor()
        );
    }

    public static Epigraph toEntity(EpigraphDTO dto) {
        if (dto == null) return null;
        Epigraph epigraph = new Epigraph();
        epigraph.setId(dto.getId());
        epigraph.setQuote(dto.getQuote());
        epigraph.setAuthor(dto.getAuthor());
        return epigraph;
    }
}