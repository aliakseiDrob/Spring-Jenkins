package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.assembler.GiftCertificateModelAssembler;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/giftCertificates")
@RequiredArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService service;
    private final PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler;
    private final GiftCertificateModelAssembler giftCertificateModelAssembler;

    @GetMapping
    public PagedModel<GiftCertificateModel> getAll(Pageable pageable) {
        return pagedResourcesAssembler.toModel(service.findAll(pageable), giftCertificateModelAssembler);
    }

    @GetMapping("/{id}")
    public GiftCertificateModel getGiftCertificate(@PathVariable Long id) {
        return giftCertificateModelAssembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GiftCertificateModel save(@RequestBody GiftCertificateDto dto) {
        return giftCertificateModelAssembler.toModel(service.save(dto));
    }

    @GetMapping("/search")
    public PagedModel<GiftCertificateModel> find(@RequestParam(required = false) Set<String> tagNames,
                                                 @RequestParam(required = false, defaultValue = "") String partNameOrDesc,
                                                 Pageable pageable) {
        return pagedResourcesAssembler.toModel(service.search(tagNames, partNameOrDesc, pageable), giftCertificateModelAssembler);
    }

    @PatchMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GiftCertificateModel update(@RequestBody GiftCertificateDto dto) {
        return giftCertificateModelAssembler.toModel(service.update(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable Long id) {
        service.delete(id);
    }
}