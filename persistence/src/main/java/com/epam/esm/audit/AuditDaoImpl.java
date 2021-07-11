package com.epam.esm.audit;

import com.epam.esm.entity.Audit;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AuditDaoImpl implements AuditDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Audit audit) {
        entityManager.persist(audit);
    }
}
