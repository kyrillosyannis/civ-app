package com.civiliansconnection.capp.convert;

import com.civiliansconnection.capp.domain.Petition;
import com.civiliansconnection.capp.dto.PetitionDto;
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
