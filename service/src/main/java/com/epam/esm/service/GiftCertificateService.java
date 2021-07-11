package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.lang.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * Interface for serving GiftCertificate objects according to the business logic of GiftCertificate
 */
public interface GiftCertificateService {

    /**
     * finds a sublist of GiftCertificates
     *
     * @param pageable object for pagination information
     * @return a sublist of GiftCertificates
     */
    Page<GiftCertificate> findAll(Pageable pageable);

    /**
     * finds a required GiftCertificate entity
     *
     * @param id GiftCertificate id
     * @return GiftCertificate Entity
     */
    GiftCertificate findById(Long id);

    /**
     * finds a sublist of GiftCertificates by specified parameters
     *
     * @param tagNames         set of Tag's names
     * @param partOfNameOrDesc part of name or part of description
     * @param pageable         object for pagination information
     * @return a sublist of GiftCertificates
     */
    Page<GiftCertificate> search(@Nullable Set<String> tagNames, @Nullable String partOfNameOrDesc,
                                 @Nullable Pageable pageable);


    /**
     * Saves  GiftCertificateDto
     *
     * @param dto GiftCertificateDto entity
     * @return GiftCertificate id from data base
     */
    GiftCertificate save(GiftCertificateDto dto);


    /**
     * updates  GiftCertificateDto
     *
     * @param certificateDto GiftCertificateDto entity
     * @return updated GiftCertificate entity
     */
    GiftCertificate update(GiftCertificateDto certificateDto);

    /**
     * Removes  GiftCertificate
     *
     * @param id GiftCertificate id
     */
    void delete(Long id);
}
