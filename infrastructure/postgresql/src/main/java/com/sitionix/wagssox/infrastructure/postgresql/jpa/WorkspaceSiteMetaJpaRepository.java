package com.sitionix.wagssox.infrastructure.postgresql.jpa;

import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceSiteMetaJpaRepository extends JpaRepository<WorkspaceSiteMetaEntity, UUID> {

    Slice<WorkspaceSiteMetaEntity> findByUserIdAndStatus_IdNotAndDeletedAtIsNull(
            Long userId,
            Long excludedStatusId,
            Pageable pageable
    );
}
