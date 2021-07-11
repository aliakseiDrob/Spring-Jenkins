package com.epam.esm.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class GiftCertificateDto implements Serializable {
    Long id;
    String name;
    String description;
    BigDecimal price;
    int duration;
    Set<TagDto> tags;
}
