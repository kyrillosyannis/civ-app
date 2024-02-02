package com.civiliansconnection.civ.controller;

import com.civiliansconnection.civ.dto.PetitionDto;
import com.civiliansconnection.civ.service.PetitionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/petitions")
public class PetitionController {

    private final PetitionService petitionService;

    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<PetitionDto> findAll(@RequestParam(value ="page", defaultValue = "0") Integer page,
                                     @RequestParam(value ="pageSize", defaultValue = "10") Integer pageSize,
                                     @RequestParam(value ="sortBy", defaultValue = "id") String sortBy,
                                     @RequestParam(value ="sortDirection", defaultValue = "DESC") String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<PetitionDto> petitionDtos = petitionService.findAll(pageable);
        return petitionDtos;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetitionDto> create(@RequestBody PetitionDto petitionDto) {
        petitionDto = petitionService.save(petitionDto);
        return new ResponseEntity(petitionDto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        petitionService.deleteById(id);
    }

    @PostMapping(path = "/{id}/signatures", produces = MediaType.APPLICATION_JSON_VALUE)
    public PetitionDto addSignature(@PathVariable("id") Long id) {
        return petitionService.addSignature(id);
    }
}
