package com.civiliansconnection.civ.convert;

import com.civiliansconnection.civ.domain.Petition;
import com.civiliansconnection.civ.dto.PetitionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PetitionDtoToPetitionConverter implements Converter<PetitionDto, Petition> {

    @Override
    public Petition convert(PetitionDto petitionDto) {
        return Petition
                .builder()
                .id(petitionDto.getId())
                .title(petitionDto.getTitle())
                .description(petitionDto.getDescription())
                .sectorId(petitionDto.getSectorId())
                .userId(petitionDto.getUserId())
                .numberOfSignatures(petitionDto.getNumberOfSignatures() == null ? 0L : petitionDto.getNumberOfSignatures())
                .build();
    }
}
