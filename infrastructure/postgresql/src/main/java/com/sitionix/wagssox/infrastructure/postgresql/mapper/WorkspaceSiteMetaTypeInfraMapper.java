package com.sitionix.wagssox.infrastructure.postgresql.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaTypeEntity;
import org.mapstruct.Mapper;

import static java.util.Objects.isNull;

@Mapper(componentModel = "spring")
public interface WorkspaceSiteMetaTypeInfraMapper {

    default WorkspaceSiteMetaType asType(final WorkspaceSiteMetaTypeEntity typeEntity) {
        if (isNull(typeEntity)) {
            return null;
        }
        return WorkspaceSiteMetaType.fromId(typeEntity.getId());
    }

    default WorkspaceSiteMetaTypeEntity asTypeEntity(final WorkspaceSiteMetaType type) {
        if (isNull(type)) {
            return null;
        }
        return WorkspaceSiteMetaTypeEntity.builder()
                .id(type.getId())
                .code(type.name())
                .build();
    }
}
