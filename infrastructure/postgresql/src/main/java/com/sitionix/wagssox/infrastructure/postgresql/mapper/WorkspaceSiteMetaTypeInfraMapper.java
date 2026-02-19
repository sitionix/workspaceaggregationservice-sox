package com.sitionix.wagssox.infrastructure.postgresql.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkspaceSiteMetaTypeInfraMapper {

    default WorkspaceSiteMetaType asType(final WorkspaceSiteMetaTypeEntity typeEntity) {
        if (typeEntity == null || typeEntity.getCode() == null) {
            return null;
        }
        return WorkspaceSiteMetaType.valueOf(typeEntity.getCode());
    }

    default WorkspaceSiteMetaTypeEntity asTypeEntity(final WorkspaceSiteMetaType type) {
        if (type == null) {
            return null;
        }
        return switch (type) {
            case PORTFOLIO -> new WorkspaceSiteMetaTypeEntity(1L, WorkspaceSiteMetaType.PORTFOLIO.name());
            case BUSINESS -> new WorkspaceSiteMetaTypeEntity(2L, WorkspaceSiteMetaType.BUSINESS.name());
            case BLOG -> new WorkspaceSiteMetaTypeEntity(3L, WorkspaceSiteMetaType.BLOG.name());
            case STORE -> new WorkspaceSiteMetaTypeEntity(4L, WorkspaceSiteMetaType.STORE.name());
            case LANDING -> new WorkspaceSiteMetaTypeEntity(5L, WorkspaceSiteMetaType.LANDING.name());
            case OTHER -> new WorkspaceSiteMetaTypeEntity(6L, WorkspaceSiteMetaType.OTHER.name());
        };
    }
}
