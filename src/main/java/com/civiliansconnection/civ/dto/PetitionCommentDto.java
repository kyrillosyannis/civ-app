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
public class PetitionCommentDto {

    private Long id;
    private String content;
    private Long userId;
    private Long petitionId;
}
