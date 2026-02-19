package com.sitionix.wagssox.infrastructure.postgresql.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkspaceSiteMetaStatusInfraMapper {

    default WorkspaceSiteMetaStatus asStatus(final WorkspaceSiteMetaStatusEntity statusEntity) {
        if (statusEntity == null || statusEntity.getCode() == null) {
            return null;
        }
        return WorkspaceSiteMetaStatus.valueOf(statusEntity.getCode());
    }

    default WorkspaceSiteMetaStatusEntity asStatusEntity(final WorkspaceSiteMetaStatus status) {
        if (status == null) {
            return null;
        }
        return switch (status) {
            case DRAFT -> new WorkspaceSiteMetaStatusEntity(1L, WorkspaceSiteMetaStatus.DRAFT.name());
            case PUBLISHED -> new WorkspaceSiteMetaStatusEntity(2L, WorkspaceSiteMetaStatus.PUBLISHED.name());
            case ARCHIVED -> new WorkspaceSiteMetaStatusEntity(3L, WorkspaceSiteMetaStatus.ARCHIVED.name());
        };
    }
}
