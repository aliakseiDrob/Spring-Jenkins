package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.ServiceUtils;
import com.epam.esm.validator.CertificateValidator;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String CERTIFICATE_NOT_FOUND = "Certificate not found";
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper mapper;
    private final ModelMapper modelMapper;
    private final CertificateValidator certificateValidator;

    @Override
    public Page<GiftCertificate> findAll(Pageable pageable) {
        Page<GiftCertificate> page = giftCertificateRepository.findAllAvailable(pageable);
        if (page.getNumberOfElements() == 0) {
            throw new PageNotFoundException("Page not found", ErrorCode.PAGE_NOT_EXISTS_ERROR.getCode());
        }
        return page;
    }

    @Override
    public GiftCertificate findById(Long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CERTIFICATE_NOT_FOUND, ErrorCode.CERTIFICATE_NOT_FOUND_ERROR.getCode()));
    }

    @Override
    public Page<GiftCertificate> search(Set<String> tagNames, String partOfNameOrDesc, Pageable pageable) {
        Page<GiftCertificate> page = new PageImpl<>(new ArrayList<>());
        if (ServiceUtils.isTagNamesPassed(tagNames) && ServiceUtils.isParameterPassed(partOfNameOrDesc)) {
            page = giftCertificateRepository.findByTagAndPartNameOrDescription(tagNames, tagNames.size(), partOfNameOrDesc, pageable);
        } else {
            if (ServiceUtils.isTagNamesPassed(tagNames)) {
                page = giftCertificateRepository.findAllAvailableByTagName(tagNames, tagNames.size(), pageable);
            }
            if (ServiceUtils.isParameterPassed(partOfNameOrDesc)) {
                page = giftCertificateRepository.findAllAvailableByNameOrDescription(partOfNameOrDesc, pageable);
            }
        }
        if (page.getNumberOfElements() == 0) {
            throw new PageNotFoundException("Page not found", ErrorCode.PAGE_NOT_EXISTS_ERROR.getCode());
        }
        return page;
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificateDto dto) {
        certificateValidator.validateCertificateForSave(dto);
        if (dto.getId() != null) {
            dto.setId(0L);
        }
        Set<Tag> tags = dto.getTags().stream()
                .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                .collect(Collectors.toSet());
        saveTags(tags);
        GiftCertificate certificate = modelMapper.map(dto, GiftCertificate.class);
        certificate.setTags(tags);
        return giftCertificateRepository.save(certificate);
    }

    //package access for testing
    void saveTags(Set<Tag> tags) {
        saveUnregisterTags(tags);
        getRegisterTagIds(tags);
    }

    //package access for testing
    void saveUnregisterTags(Set<Tag> tags) {
        tags.stream()
                .filter(tag -> !tagRepository.existsTagByName(tag.getName()))
                .forEach(tag -> tag = tagRepository.save(tag));
    }

    //package access for testing
    void getRegisterTagIds(Set<Tag> tags) {
        tags.forEach(tag -> tagRepository.findByName(tag.getName()).ifPresent(result -> tag.setId(result.getId())));
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificateDto certificateDto) {
        certificateValidator.validateCertificateForUpdate(certificateDto);
        GiftCertificate certificate = giftCertificateRepository.findById(certificateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(CERTIFICATE_NOT_FOUND, ErrorCode.CERTIFICATE_NOT_FOUND_ERROR.getCode()));
        Set<Tag> tags = certificateDto.getTags().stream()
                .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                .collect(Collectors.toSet());
        if (!tags.isEmpty()) {
            saveTags(tags);
        }
        certificate = mapper.updateGiftCertificateFromDto(certificateDto, certificate);
        certificate.setTags(tags);
        return giftCertificateRepository.save(certificate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        GiftCertificate certificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CERTIFICATE_NOT_FOUND, ErrorCode.CERTIFICATE_NOT_FOUND_ERROR.getCode()));
        certificate.setIsAvailable(0);
        giftCertificateRepository.save(certificate);
    }
}
