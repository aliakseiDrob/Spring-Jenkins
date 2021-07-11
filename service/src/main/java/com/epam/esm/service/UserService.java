package com.epam.esm.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Interface for serving User objects according to the business logic of User
 */
public interface UserService {

    /**
     * finds a sublist of Users
     *
     * @param pageable object for pagination information
     * @return a sublist of Users
     */
    Page<User> findAll(Pageable pageable);

    /**
     * finds a required user entity
     *
     * @param id User id
     * @return User Entity
     */
    User findById(Long id);

    /**
     * finds a required User entity by name
     *
     * @param name User name
     * @return Optional User can be null
     */
    Optional<User> findByName(String name);

    /**
     * Saves  UserDto
     *
     * @param dto UserDto entity
     * @return User id
     */
    UserDto save(UserDto dto);
}
