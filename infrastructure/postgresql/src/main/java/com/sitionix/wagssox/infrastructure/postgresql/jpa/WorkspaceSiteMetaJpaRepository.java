package com.sitionix.wagssox.infrastructure.postgresql.jpa;

import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceSiteMetaJpaRepository extends JpaRepository<WorkspaceSiteMetaEntity, UUID> {

    List<WorkspaceSiteMetaEntity> findAllByOwnerUserIdOrderByUpdatedAtDesc(Long ownerUserId);
}
