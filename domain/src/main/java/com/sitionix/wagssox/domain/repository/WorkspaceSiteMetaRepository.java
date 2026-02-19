package com.sitionix.wagssox.domain.repository;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for read-model metadata of user sites shown in workspace.
 */
public interface WorkspaceSiteMetaRepository {

    /**
     * Saves or updates a workspace site metadata record.
     *
     * @param siteMeta projection state to persist.
     * @return persisted projection state.
     */
    WorkspaceSiteMeta save(WorkspaceSiteMeta siteMeta);

    /**
     * Finds metadata by site identifier.
     *
     * @param siteId site identifier.
     * @return optional metadata entry.
     */
    Optional<WorkspaceSiteMeta> findBySiteId(UUID siteId);

}
