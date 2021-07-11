package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "giftCertificates"}, callSuper = false)
@EntityListeners(AuditListener.class)

@SqlResultSetMapping(name = "mostWidelyUsedTagMapper",
        classes = {
                @ConstructorResult(targetClass = MostWidelyUsedTag.class,
                        columns = {
                                @ColumnResult(name = "tag_id", type = Long.class),
                                @ColumnResult(name = "tag_name", type = String.class),
                                @ColumnResult(name = "highest_cost", type = BigDecimal.class)
                        })})

public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    private Set<GiftCertificate> giftCertificates;

    public Tag(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }
}