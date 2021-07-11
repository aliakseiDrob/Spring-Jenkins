package com.epam.esm.validator.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.CertificateValidationException;
import com.epam.esm.validator.CertificateValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.epam.esm.exception.ErrorCode.*;

@Component
public class CertificateValidatorImpl implements CertificateValidator {
    private static final int MAX_CERTIFICATE_NAME_LENGTH = 64;
    private static final int MAX_CERTIFICATE_DESCRIPTION_LENGTH = 255;

    @Override
    public void validateCertificateForSave(GiftCertificateDto certificate) {
        validateCertificateName(certificate.getName());
        validateCertificatePrice(certificate.getPrice());
        validateCertificateDuration(certificate.getDuration());
        validateCertificateDescription(certificate.getDescription());
    }

    /**
     * Validates CertificateDto name before saving in database
     *
     * @param name name of CertificateDto entity
     * @throws CertificateValidationException if CertificateDto name  not valid
     **/
    //package access for testing
    void validateCertificateName(String name) {
        if (StringUtils.isBlank(name) || name.length() > MAX_CERTIFICATE_NAME_LENGTH) {
            throw new CertificateValidationException("Certificate name can't be empty or be more then 64 characters",
                    CERTIFICATE_EMPTY_NAME_OR_LENGTH_ERROR.getCode());
        }
    }

    /**
     * Validates CertificateDto description before saving in database
     *
     * @param description description of CertificateDto entity
     * @throws CertificateValidationException if CertificateDto description  not valid
     **/
    //package access for testing
    void validateCertificateDescription(String description) {
        if (StringUtils.isBlank(description) || description.length() > MAX_CERTIFICATE_DESCRIPTION_LENGTH) {
            throw new CertificateValidationException("Certificate description can't be empty or more than 255 characters",
                    CERTIFICATE_EMPTY_DESCRIPTION_OR_LENGTH_DESCRIPTION_ERROR.getCode());
        }
    }

    /**
     * Validates CertificateDto price before saving in database
     *
     * @param price price of CertificateDto entity
     * @throws CertificateValidationException if CertificateDto price  not valid
     **/
    //package access for testing
    void validateCertificatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CertificateValidationException("price can't be less than zero", CERTIFICATE_PRICE_ERROR.getCode());
        }
    }

    /**
     * Validates CertificateDto duration before saving in database
     *
     * @param duration duration of CertificateDto entity
     * @throws CertificateValidationException if CertificateDto duration  not valid
     **/
    //package access for testing
    void validateCertificateDuration(int duration) {
        if (duration <= 0 || duration > 31) {
            throw new CertificateValidationException("duration must be between 1 and 31", CERTIFICATE_DURATION_ERROR.getCode());
        }
    }

    @Override
    public void validateCertificateForUpdate(GiftCertificateDto certificate) {
        validateUpdatedCertificateName(certificate.getName());
        validateUpdatedCertificatePrice(certificate.getPrice());
        validateUpdatedDuration(certificate.getDuration());
        validateUpdatedDescription(certificate.getDescription());
    }

    /**
     * Validates CertificateDto name before updating in database
     *
     * @param name name of CertificateDto entity
     * @throws CertificateValidationException if CertificateDto name not valid
     **/
    //package access for testing
    private void validateUpdatedCertificateName(String name) {
        if (name != null) {
            validateCertificateName(name);
        }
    }

    /**
     * Validates CertificateDto description before updating in database
     *
     * @param description description of CertificateDto entity
     * @throws CertificateValidationException if CertificateDto description not valid
     **/
    //package access for testing
    private void validateUpdatedDescription(String description) {
        if (description != null) {
            validateCertificateDescription(description);
        }
    }

    /**
     * Validates CertificateDto duration before updating in database
     *
     * @param duration duration of CertificateDto entity
     * @throws CertificateValidationException if CertificateDto duration not valid
     **/
    //package access for testing
    private void validateUpdatedDuration(int duration) {
        if (duration != 0) {
            validateCertificateDuration(duration);
        }
    }

    /**
     * Validates CertificateDto price before updating in database
     *
     * @param price price of CertificateDto entity
     * @throws CertificateValidationException if CertificateDto price not valid
     **/
    //package access for testing
    void validateUpdatedCertificatePrice(BigDecimal price) {
        if (price != null) {
            validateCertificatePrice(price);
        }
    }
}
