package com.civiliansconnection.civ.service;

import com.civiliansconnection.civ.domain.Petition;
import com.civiliansconnection.civ.domain.PetitionComment;
import com.civiliansconnection.civ.dto.PetitionCommentDto;
import com.civiliansconnection.civ.dto.PetitionDto;
import com.civiliansconnection.civ.exception.CivException;
import com.civiliansconnection.civ.repository.PetitionCommentRepository;
import com.civiliansconnection.civ.repository.PetitionRepository;
import com.civiliansconnection.civ.security.CivUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class PetitionService {

    private final PetitionRepository petitionRepository;
    private final PetitionCommentRepository petitionCommentRepository;
    private final ConversionService conversionService;

    public PetitionService(PetitionRepository petitionRepository, PetitionCommentRepository petitionCommentRepository,
                           ConversionService conversionService) {
        this.petitionRepository = petitionRepository;
        this.conversionService = conversionService;
        this.petitionCommentRepository = petitionCommentRepository;
    }

    public PetitionDto save(PetitionDto petitionDto) {
        log.info("Saving petition for sector: {} user:{} petition-id: {}", petitionDto.getSectorId(), petitionDto.getUserId(), petitionDto.getId());
        Petition petition = conversionService.convert(petitionDto, Petition.class);
        petition = petitionRepository.save(petition);
        petitionDto = conversionService.convert(petition, PetitionDto.class);
        log.info("Saved petition for sector: {} user:{} petition-id: {}", petitionDto.getSectorId(), petitionDto.getUserId(), petitionDto.getId());
        return petitionDto;
    }

    public Page<PetitionDto> findAll(Pageable pageable) {
        log.info("Finding all petitions page-size: {}", pageable.getPageSize());
        Page<Petition> petitionPage = petitionRepository.findAll(pageable);
        List<PetitionDto> petitionDtoList = petitionPage
                .map(petition -> conversionService.convert(petition, PetitionDto.class))
                .toList();
        //get comments number
        return new PageImpl<>(petitionDtoList, pageable, petitionPage.getTotalElements());
    }

    public void deleteById(Long id) {
        log.info("Deleting petition with id: {}", id);
        petitionRepository.deleteById(id);
    }

    public PetitionDto addSignature(Long id) {
        CivUserDetails principal = (CivUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Adding signature for petition: {}", id);
        Petition petition = petitionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Petition does not exist with id" + id));
        if (principal.getId().equals(petition.getUserId())) {
            throw new CivException("User cannot vote for their own petition");
        }
        petition.setNumberOfSignatures(petition.getNumberOfSignatures() + 1);
        petition = petitionRepository.save(petition);
        PetitionDto petitionDto = conversionService.convert(petition, PetitionDto.class);
        return petitionDto;
    }

    public PetitionCommentDto saveComment(PetitionCommentDto petitionCommentDto) {
        CivUserDetails principal = (CivUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Saving comment for petition: {} by user: {}", petitionCommentDto.getPetitionId(), principal.getId());
        PetitionComment petitionComment = conversionService.convert(petitionCommentDto, PetitionComment.class);
        petitionCommentRepository.save(petitionComment);
        petitionCommentDto = conversionService.convert(petitionComment, PetitionCommentDto.class);
        return petitionCommentDto;
    }

    public Page<PetitionCommentDto> getCommentsByPetitionId(Long petitionId) {
        log.info("Getting comments for petition: {}", petitionId);
        Sort sort = Sort.by(Sort.Direction.fromString("DESC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<PetitionComment> petitionCommentsPage = petitionCommentRepository.findAll(pageable);
        List<PetitionCommentDto> petitionCommentDtos = petitionCommentsPage
                .map(petitionComment -> conversionService.convert(petitionComment, PetitionCommentDto.class))
                .toList();
        return new PageImpl<>(petitionCommentDtos, pageable, petitionCommentsPage.getTotalElements());
    }
}
