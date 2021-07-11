package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
public class UserRepositoryTest {

    @Autowired
   private UserRepository userRepository;

    @Test
    public void testFindByNameShouldReturnUserByName() {
        //given
        Optional<User> optionalUser = Optional.of(new User(1L, "Ivan", "ivan", null, null));
        //then
        assertEquals(optionalUser, userRepository.findByName("Ivan"));
    }

    @Test
    public void testFindByNameShouldReturnEmptyUserIfNotExists() {
        //given
        Optional<User> emptyUser = Optional.empty();
        //then
        assertEquals(emptyUser, userRepository.findByName("Nikola"));
    }

    @Test
    public void testFindByIdShouldReturnUserById() {
        //given
        Optional<User> optionalUser = Optional.of(new User(1L, "Ivan", "ivan", null, null));
        //then
        assertEquals(optionalUser, userRepository.findById(1L));
    }

    @Test
    public void testFindByIdShouldReturnEmptyUserIfNotExists() {
        //given
        Optional<User> emptyUser = Optional.empty();
        //then
        assertEquals(emptyUser, userRepository.findById(7L));
    }
}
