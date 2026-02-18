package com.sitionix.wagssox.infrastructure.postgresql.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceSiteMetaInfraMapper {

    public WorkspaceSiteMetaEntity asEntity(final WorkspaceSiteMeta siteMeta) {
        return new WorkspaceSiteMetaEntity(
                siteMeta.siteId(),
                siteMeta.ownerUserId(),
                siteMeta.name(),
                siteMeta.status(),
                siteMeta.type(),
                siteMeta.description(),
                siteMeta.createdAt(),
                siteMeta.updatedAt()
        );
    }

    public WorkspaceSiteMeta asDomain(final WorkspaceSiteMetaEntity siteMetaEntity) {
        return new WorkspaceSiteMeta(
                siteMetaEntity.getSiteId(),
                siteMetaEntity.getOwnerUserId(),
                siteMetaEntity.getName(),
                siteMetaEntity.getStatus(),
                siteMetaEntity.getType(),
                siteMetaEntity.getDescription(),
                siteMetaEntity.getCreatedAt(),
                siteMetaEntity.getUpdatedAt()
        );
    }
}
