package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for serving Tag objects according to the business logic of Tag
 */
public interface TagService {


    /**
     * finds a sublist of Tags
     *
     * @param pageable object for pagination information
     * @return a sublist of Tags
     */
    Page<Tag> findAll(Pageable pageable);

    /**
     * finds a required Tag entity
     *
     * @param id Tag id
     * @return Tag Entity
     */
    Tag findById(Long id);

    /**
     * Saves  TagDto
     *
     * @param tagDto TagDto entity
     * @return Tag
     */
    Tag save(TagDto tagDto);

    /**
     * Removes  TagDto
     *
     * @param id TagDto id
     */
    void delete(Long id);

    /**
     * finds a MostWildlyUsedTag entity
     *
     * @param userId User id
     * @return MostWildlyUsedTag Entity
     */
    MostWidelyUsedTag getMostWidelyUsedTag(Long userId);
}
