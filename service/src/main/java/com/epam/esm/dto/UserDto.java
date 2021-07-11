package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserDto implements Serializable {
    private String name;
    private String password;

    public UserDto() {
    }
}
