package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for Tag entity
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * finds Tag by name
     *
     * @param name tag name
     * @return Optional tag can be null
     */
    Optional<Tag> findByName(String name);

    /**
     * checks if Tag exists
     *
     * @param name Tag name
     * @return boolean value, if Tag exists, return true, else false
     */
    boolean existsTagByName(String name);
}
