package com.epam.esm.audit;

import com.epam.esm.entity.Audit;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class AuditListener {

    private AuditDao auditDao;

    public AuditListener() {
    }

    @PostPersist
    public void prePersist(Object identifiable) {
        this.registerOperation(identifiable, TypeOperation.CREATE);
    }

    @PostUpdate
    public void preUpdate(Object identifiable) {
        this.registerOperation(identifiable, TypeOperation.UPDATE);
    }

    @PostRemove
    public void preRemove(Object identifiable) {
        this.registerOperation(identifiable, TypeOperation.DELETE);
    }

    private void registerOperation(Object identifiable, TypeOperation operation) {

        if (auditDao == null) {
            auditDao = AuditDaoFactory.getBean(AuditDaoImpl.class);
        }
        String entity = identifiable.toString();
        Audit audit = new Audit(null, operation.name(), null, entity);
        auditDao.save(audit);
    }
}
