package com.civiliansconnection.civ.convert;

import com.civiliansconnection.civ.domain.PetitionComment;
import com.civiliansconnection.civ.dto.PetitionCommentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PetitionCommentDtoToPetitionCommentConverter implements Converter<PetitionCommentDto, PetitionComment> {

    @Override
    public PetitionComment convert(PetitionCommentDto petitionCommentDto) {
        PetitionComment petitionComment = PetitionComment
                .builder()
                .id(petitionCommentDto.getId())
                .content(petitionCommentDto.getContent())
                .petitionId(petitionCommentDto.getPetitionId())
                .userId(petitionCommentDto.getUserId())
                .build();
        return petitionComment;
    }
}
