package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@EqualsAndHashCode(exclude = {"id", "certificate"})
@EntityListeners(AuditListener.class)

public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", updatable = false)
    private LocalDateTime orderDate;
    @Column(name = "order_cost", updatable = false)
    private BigDecimal orderCost;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "certificate_id", nullable = false)
    private GiftCertificate certificate;

    @PrePersist
    private void setParameters() {
        setDate();
        setCost();
    }

    private void setDate() {
        LocalDateTime localDate = LocalDateTime.now();
        setOrderDate(localDate);
    }

    private void setCost() {
        setOrderCost(getCertificate().getPrice());
    }
}
