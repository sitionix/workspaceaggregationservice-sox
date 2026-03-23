package com.sitionix.wagssox.domain.repository;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

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

    /**
     * Loads active site metadata for a specific user and site identifier.
     *
     * @param userId user identifier.
     * @param siteId site identifier.
     * @return active site metadata when accessible for the user.
     */
    Optional<WorkspaceSiteMeta> findActiveByUserIdAndSiteId(Long userId, UUID siteId);

    /**
     * Loads non-archived site metadata for a user in descending update order.
     *
     * @param userId user identifier.
     * @param pageable page request with paging and sorting.
     * @return ordered page of records.
     */
    WorkspaceSitesPage findActiveByUserId(Long userId, Pageable pageable);

}
