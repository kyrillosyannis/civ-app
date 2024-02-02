package com.civiliansconnection.civ.convert;

import com.civiliansconnection.civ.domain.Petition;
import com.civiliansconnection.civ.dto.PetitionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PetitionToPetitionDtoConverter implements Converter<Petition, PetitionDto> {

    @Override
    public PetitionDto convert(Petition petition) {
        return PetitionDto
                .builder()
                .id(petition.getId())
                .title(petition.getTitle())
                .description(petition.getDescription())
                .sectorId(petition.getSectorId())
                .userId(petition.getUserId())
                .numberOfSignatures(petition.getNumberOfSignatures() == null ? 0L : petition.getNumberOfSignatures())
                .build();
    }
}
