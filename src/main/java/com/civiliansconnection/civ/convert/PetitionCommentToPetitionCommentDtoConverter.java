package com.civiliansconnection.civ.convert;

import com.civiliansconnection.civ.domain.PetitionComment;
import com.civiliansconnection.civ.dto.PetitionCommentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PetitionCommentToPetitionCommentDtoConverter implements Converter<PetitionComment, PetitionCommentDto> {

    @Override
    public PetitionCommentDto convert(PetitionComment petitionComment) {
        PetitionCommentDto petitionCommentDto = PetitionCommentDto
                .builder()
                .id(petitionComment.getId())
                .content(petitionComment.getContent())
                .petitionId(petitionComment.getPetitionId())
                .userId(petitionComment.getUserId())
                .build();
        return petitionCommentDto;
    }
}
