package com.sitionix.wagssox.infrastructure.postgresql.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaStatusEntity;
import org.mapstruct.Mapper;

import static java.util.Objects.isNull;

@Mapper(componentModel = "spring")
public interface WorkspaceSiteMetaStatusInfraMapper {

    default WorkspaceSiteMetaStatus asStatus(final WorkspaceSiteMetaStatusEntity statusEntity) {
        if (isNull(statusEntity)) {
            return null;
        }
        return WorkspaceSiteMetaStatus.fromId(statusEntity.getId());
    }

    default WorkspaceSiteMetaStatusEntity asStatusEntity(final WorkspaceSiteMetaStatus status) {
        if (isNull(status)) {
            return null;
        }
        return WorkspaceSiteMetaStatusEntity.builder()
                .id(status.getId())
                .code(status.name())
                .build();
    }
}
