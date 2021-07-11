package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.repository.MostWidelyUsedTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.epam.esm.exception.ErrorCode.*;

@Service
@Data
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final MostWidelyUsedTagRepository mostWidelyUsedTagRepository;
    private final ModelMapper modelMapper;
    private final TagValidator tagValidator;

    @Override
    public Page<Tag> findAll(Pageable pageable) {
        Page<Tag> page = tagRepository.findAll(pageable);
        if (page.getNumberOfElements() == 0) {
            throw new PageNotFoundException("Page not found", ErrorCode.PAGE_NOT_EXISTS_ERROR.getCode());
        }
        return page;
    }

    @Override
    public Tag findById(Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        return optionalTag.orElseThrow(() -> new EntityNotFoundException("Tag not found", TAG_NOT_FOUND_ERROR.getCode()));
    }

    @Override
    @Transactional
    public Tag save(TagDto tagDto) {
        if (!tagValidator.isTagValid(tagDto)) {
            throw new TagValidationException("Tag name can't be empty or more than 64 characters",
                    TAG_EMPTY_NAME_OR_LENGTH_ERROR.getCode());
        }
        Tag tag = modelMapper.map(tagDto, Tag.class);
        if (tag.getId() != null) {
            tag.setId(0L);
        }
        try {
            return tagRepository.save(tag);
        } catch (
                DataIntegrityViolationException exception) {
            throw new TagEntityException("Tag already exists", TAG_EXISTS_ERROR.getCode());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        Tag tag = optionalTag.orElseThrow(() -> new EntityNotFoundException("Tag not found", TAG_NOT_FOUND_ERROR.getCode()));
        tagRepository.delete(tag);
    }

    @Override
    public MostWidelyUsedTag getMostWidelyUsedTag(Long userId) {
        return mostWidelyUsedTagRepository.findMostWidelyUsedTag(userId);
    }
}
