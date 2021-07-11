package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;

/**
 * Interface for validation TagDto objects according to the business logic of TagDto
 */
public interface TagValidator {

    /**
     * Validates TagDto entity
     *
     * @param tag TagDto entity
     * @return false if TagDto not valid
     **/
    boolean isTagValid(TagDto tag);
}
