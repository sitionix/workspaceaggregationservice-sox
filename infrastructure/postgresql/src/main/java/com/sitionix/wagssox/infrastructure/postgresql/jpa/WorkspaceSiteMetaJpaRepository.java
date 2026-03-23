package com.sitionix.wagssox.infrastructure.postgresql.jpa;

import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkspaceSiteMetaJpaRepository extends JpaRepository<WorkspaceSiteMetaEntity, UUID> {

    @Query("""
            SELECT siteMeta
            FROM WorkspaceSiteMetaEntity siteMeta
            WHERE siteMeta.userId = :userId
              AND siteMeta.status.id <> :excludedStatusId
            """)
    Page<WorkspaceSiteMetaEntity> findActiveByUserId(
            @Param("userId") Long userId,
            @Param("excludedStatusId") Long excludedStatusId,
            Pageable pageable
    );

    @Query("""
            SELECT siteMeta
            FROM WorkspaceSiteMetaEntity siteMeta
            WHERE siteMeta.userId = :userId
              AND siteMeta.siteId = :siteId
              AND siteMeta.status.id <> :excludedStatusId
            """)
    Optional<WorkspaceSiteMetaEntity> findActiveByUserIdAndSiteId(
            @Param("userId") Long userId,
            @Param("siteId") UUID siteId,
            @Param("excludedStatusId") Long excludedStatusId
    );
}
