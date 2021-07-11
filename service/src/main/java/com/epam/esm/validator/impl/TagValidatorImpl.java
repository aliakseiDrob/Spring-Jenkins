package com.epam.esm.validator.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.validator.TagValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TagValidatorImpl implements TagValidator {
    private static final int MAX_TAG_NAME_LENGTH = 64;

    @Override
    public boolean isTagValid(TagDto tag) {
        if (StringUtils.isBlank(tag.getName())){
            return false;
        }
        return tag.getName().length() <= MAX_TAG_NAME_LENGTH;
    }
}
