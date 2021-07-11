package com.epam.esm.validator.impl;

import com.epam.esm.dto.TagDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagValidatorImplTest {

    private static final TagDto VALID_TAG_DTO = new TagDto(1L,"new tag");
    private static final TagDto TAG_DTO_WITH_EMPTY_NAME = new TagDto(1L, "  ");
    private static final TagDto TAG_DTO_WITH_LONG_NAME = new TagDto(1L,
            "VERY_LONG_NAME_MORE_THEN_64_CHARACTERS_VERY_LONG_NAME_MORE_THEN_64_CHARACTERS");
    private final TagValidatorImpl tagValidator = new TagValidatorImpl();

    @Test
    public void testIsTagValidShouldReturnTrueIfTagNameValid(){
        //then
        assertTrue(tagValidator.isTagValid(VALID_TAG_DTO));
    }

    @Test
    public void testIsTagValidShouldReturnFalseIfTagNameEmpty() {
        //then
        assertFalse(tagValidator.isTagValid(TAG_DTO_WITH_EMPTY_NAME));
    }

    @Test
    public void testIsTagValidShouldReturnFalseIfTagNameMoreThenExpected() {
        //then
        assertFalse(tagValidator.isTagValid(TAG_DTO_WITH_LONG_NAME));
    }
}

