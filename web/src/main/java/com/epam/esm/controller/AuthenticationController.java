package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.security.AuthenticationDto;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Data
@RequestMapping("/authentication")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public UserDto register(@RequestBody UserDto dto) {
        return userService.save(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDto dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getName(), dto.getPassword()));
        User user = userService.findByName(dto.getName()).orElseThrow(() -> new UsernameNotFoundException("user not exists"));
        String token = jwtTokenProvider.createToken(dto.getName(), user.getRoles());
        return createResponseEntity(dto.getName(), token);
    }

    private ResponseEntity<?> createResponseEntity(String name, String token) {
        Map<Object, Object> response = new HashMap<>();
        response.put("name", name);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}