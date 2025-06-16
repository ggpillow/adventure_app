package com.kolyma.adventure.mapper;

import com.kolyma.adventure.dto.ParagraphDTO;
import com.kolyma.adventure.model.Paragraph;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParagraphMapper {

    public static ParagraphDTO toDTO(Paragraph paragraph) {
        if (paragraph == null) return null;
        return new ParagraphDTO(
                paragraph.getId(),
                paragraph.getParagraphNumber(),
                paragraph.getParagraphDescr(),
                paragraph.getEffect() != null ? paragraph.getEffect().getId() : null
        );
    }

    public static Optional<ParagraphDTO> toDTO(Optional<Paragraph> paragraphOpt) {
        return paragraphOpt.map(ParagraphMapper::toDTO);
    }

    public static Paragraph toEntity(ParagraphDTO dto) {
        if (dto == null) return null;
        Paragraph paragraph = new Paragraph();
        paragraph.setId(dto.getId());
        paragraph.setParagraphNumber(dto.getParagraphNumber());
        paragraph.setParagraphDescr(dto.getParagraphDescr());
        // effect будет задаваться отдельно в сервисе
        return paragraph;
    }
}