package com.epam.esm.repository;

import com.epam.esm.entity.MostWidelyUsedTag;

/**
 * Repository interface for MostWildlyUsedTag entity
 */
public interface MostWidelyUsedTagRepository {

    /**
     * finds MostWildlyUsedTag Entity by user id
     *
     * @param userId user's id
     * @return MostWildlyUsedTag entity
     */
    MostWidelyUsedTag findMostWidelyUsedTag(Long userId);
}
