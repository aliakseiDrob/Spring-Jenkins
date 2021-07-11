package com.epam.esm.repository.impl;

import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.repository.MostWidelyUsedTagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MostWildlyUsedTagRepositoryImpl implements MostWidelyUsedTagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MostWidelyUsedTag findMostWidelyUsedTag(Long userId) {
        return (MostWidelyUsedTag) entityManager.createNativeQuery(
                "SELECT tag.id AS tag_id, tag.name AS tag_name , MAX(o.order_cost) AS highest_cost " +
                        "FROM tag " +
                        "JOIN gift_certificate_tag gct ON gct.tag_id = tag.id " +
                        "JOIN orders o ON o.certificate_id = gct.gift_certificate_id " +
                        "WHERE o.user_id = :userId " +
                        "GROUP BY tag.id " +
                        "ORDER BY COUNT(tag.id) DESC " +
                        "LIMIT 1",
                "mostWidelyUsedTagMapper")
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
