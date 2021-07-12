package com.epam.esm.servise.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.UserEntityException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static List<User> users;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl service;

    @BeforeAll
    public static void init() {
        users = Arrays.asList(new User(1L, "first", "password", null, null),
                new User(2, "second", "password", null, null));
    }

    @Test
    public void testFindAllShouldReturnAllUsers() {
        //given
        Page<User> usersPage = new PageImpl<>(users);
        //when
        when(userRepository.findAll(Pageable.unpaged())).thenReturn(usersPage);

        assertEquals(service.findAll(Pageable.unpaged()), usersPage);
        verify(userRepository).findAll(Pageable.unpaged());
    }

    @Test
    public void testGetByIdShouldReturnUserWhenUserExists() {
        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(users.get(0)));
        //then
        assertEquals(users.get(0), service.findById(1L));
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetByIdShouldTrowExceptionWhenUserNotExists() {
        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testFindByNameShouldReturnOptionalUser() {
        //given
        Optional<User> optionalUser = Optional.of(users.get(0));
        //when
        when(userRepository.findByName(anyString())).thenReturn(Optional.of(users.get(0)));
        //then
        assertEquals(optionalUser, service.findByName("some name"));

        verify(userRepository, times(1)).findByName(anyString());
    }

    @Test
    public void testShouldReturnSavedUserId() {
        //given
        Role role = new Role(1L, "ROLE_USER", null);
        UserDto userDto = new UserDto("name", "password");

        // when
        when(roleRepository.findByName(anyString())).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(users.get(0));

        //then
        assertEquals(userDto, service.save(userDto));

        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(anyString());

    }

    @Test
    public void testShouldThrowExceptionWhenUserExists() {
        //given
        Role role = new Role(1L, "ROLE_USER", null);
        UserDto userDto = new UserDto("name", "password");
        // when
        when(roleRepository.findByName(anyString())).thenReturn(role);
        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException(""));
        //then
        assertThrows(UserEntityException.class, () -> service.save(userDto));

        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(anyString());
    }
}
