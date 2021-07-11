package com.epam.esm.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MostWidelyUsedTag implements Serializable {

    private final Tag mostWidelyUsedTag;
    private final BigDecimal highestOrderPrice;

    public MostWidelyUsedTag(Long id, String name, BigDecimal highestOrderPrice) {
        mostWidelyUsedTag = new Tag(id, name);
        this.highestOrderPrice = highestOrderPrice;
    }
}
