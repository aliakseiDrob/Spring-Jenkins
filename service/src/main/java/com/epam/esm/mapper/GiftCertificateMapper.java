package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateMapper {

    public GiftCertificate updateGiftCertificateFromDto(GiftCertificateDto dto, GiftCertificate certificate) {
        if (dto.getName() != null) {
            certificate.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            certificate.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            certificate.setPrice(dto.getPrice());
        }
        if (dto.getDuration() != 0) {
            certificate.setDuration(dto.getDuration());
        }
       return certificate;
    }
}
