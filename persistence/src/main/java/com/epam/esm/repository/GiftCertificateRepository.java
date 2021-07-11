package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

/**
 * Repository interface for GiftCertificate entity
 */
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    /**
     * finds giftCertificate by id, if 'isAvailable=0' in row don't return that row
     *
     * @param id giftCertificate id
     * @return optional gift certificate can be null
     */
    @Query("SELECT c FROM GiftCertificate c WHERE c.id = :id and c.isAvailable = 1")
    Optional<GiftCertificate> findById(@Param("id") Long id);

    /**
     * finds all giftCertificate if 'isAvailable=0' i in row don't return that row
     *
     * @return a sublist of GiftCertificates
     */
    @Query("SELECT c FROM GiftCertificate c WHERE c.isAvailable = 1")
    Page<GiftCertificate> findAllAvailable(Pageable pageable);


    /**
     * finds all giftCertificate by tag name if 'isAvailable=0'  in row don't return that row
     *
     * @param tagNames     set of tags names  is used to filter records
     * @param tagsQuantity count of passed names of tags
     * @return a sublist of GiftCertificates
     */
    @Query("SELECT DISTINCT c FROM GiftCertificate c JOIN c.tags tag WHERE c.isAvailable=1 and tag.name IN :tags " +
            "GROUP BY c.id HAVING COUNT(c) >= :count")
    Page<GiftCertificate> findAllAvailableByTagName(@Param("tags") Set<String> tagNames,
                                                    @Param("count") long tagsQuantity,
                                                    Pageable pageable);

    /**
     * finds all giftCertificate by part of name or part of description if 'isAvailable=0' in row don't return that row
     *
     * @param text     part of name or part of description
     * @param pageable object for pagination information
     * @return a sublist of GiftCertificates
     */
    @Query("FROM GiftCertificate c WHERE c.isAvailable=1 and (c.name LIKE CONCAT('%',:text,'%') OR c.description LIKE CONCAT('%',:text,'%'))")
    Page<GiftCertificate> findAllAvailableByNameOrDescription(@Param("text") String text, Pageable pageable);

    /**
     * finds all giftCertificate by tag name or by part of name or part of description if 'isAvailable=0' in row don't return that row
     *
     * @param tagNames     set of tags names  are used to filter records
     * @param tagsQuantity count of passed names of tags
     * @param text         part of name or part of description
     * @return a sublist of GiftCertificates
     */
    @Query("SELECT DISTINCT c FROM GiftCertificate c JOIN c.tags tag WHERE c.isAvailable =1 and (tag.name IN :tags " +
            "OR c.name LIKE CONCAT ('%',:text,'%') OR c.description LIKE CONCAT ('%',:text,'%'))" +
            "GROUP BY c.id HAVING COUNT(c) >= :count")
    Page<GiftCertificate> findByTagAndPartNameOrDescription(@Param("tags") Set<String> tagNames,
                                                            @Param("count") long tagsQuantity,
                                                            @Param("text") String text,
                                                            Pageable pageable);
}
