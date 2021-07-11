package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GiftCertificateModel extends RepresentationModel<GiftCertificateModel> {

    Long id;
    String name;
    String description;
    BigDecimal price;
    LocalDateTime createDate;
    LocalDateTime lastUpdateDate;
    int duration;
    Set<TagModel> tags;
}
