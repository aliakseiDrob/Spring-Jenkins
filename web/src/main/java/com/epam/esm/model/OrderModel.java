package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderModel extends RepresentationModel<OrderModel> {

    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal orderCost;
    private UserModel userModel;
    private GiftCertificateModel certificate;
}
