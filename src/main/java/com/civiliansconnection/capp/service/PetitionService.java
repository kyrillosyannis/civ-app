package com.civiliansconnection.capp.service;

import com.civiliansconnection.capp.domain.Petition;
import com.civiliansconnection.capp.dto.PetitionDto;
import com.civiliansconnection.capp.exception.CivException;
import com.civiliansconnection.capp.repository.PetitionRepository;
import com.civiliansconnection.capp.security.CivUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class PetitionService {

    private final PetitionRepository petitionRepository;

    private final ConversionService conversionService;

    public PetitionService(PetitionRepository petitionRepository, ConversionService conversionService) {
        this.petitionRepository = petitionRepository;
        this.conversionService = conversionService;
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
}
