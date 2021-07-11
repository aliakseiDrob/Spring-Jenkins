package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.exception.UserEntityException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

import static com.epam.esm.exception.ErrorCode.USER_EXISTS_ERROR;

@Service
@Data
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<User> findAll(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        if (page.getNumberOfElements() == 0) {
            throw new PageNotFoundException("Page not found", ErrorCode.PAGE_NOT_EXISTS_ERROR.getCode());
        }
        return page;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found", USER_EXISTS_ERROR.getCode()));
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    @Transactional
    public UserDto save(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        try {
            userRepository.save(user);
            return dto;
        } catch (DataIntegrityViolationException ex) {
            throw new UserEntityException("profile with such name-for-user already exists", USER_EXISTS_ERROR.getCode());
        }
    }
}
