package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "gift_certificate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"tags", "lastUpdateDate"})
@EntityListeners(AuditListener.class)
public class GiftCertificate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private int duration;
    @Column(name = "is_available", nullable = false)
    private int isAvailable;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = {@JoinColumn(name = "gift_certificate_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )

    @JsonBackReference
    private Set<Tag> tags;

    @PrePersist
    private void setDates() {
        LocalDateTime localDateTime = LocalDateTime.now();
        setCreateDate(localDateTime);
        setLastUpdateDate(localDateTime);
        setIsAvailable(1);
    }

    @PreUpdate
    private void setUpdatableDate() {
        setLastUpdateDate(LocalDateTime.now());
    }

}
