package com.epam.esm.audit;

import com.epam.esm.entity.Audit;


/**
 * Repository interface for Audit entity
 */
public interface AuditDao {

    /** saves audit entity
     *
     * @param audit Audit entity
     */
    void save(Audit audit);
}
