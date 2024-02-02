package com.civiliansconnection.civ.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class PetitionDto {

    private Long id;
    private String title;
    private String description;
    private Long sectorId;
    private Long userId;
    private Long numberOfSignatures;
}
