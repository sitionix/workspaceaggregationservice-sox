package com.sitionix.wagssox.infrastructure.postgresql.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                WorkspaceSiteMetaStatusInfraMapper.class,
                WorkspaceSiteMetaTypeInfraMapper.class
        }
)
public interface WorkspaceSiteMetaInfraMapper {

    WorkspaceSiteMetaEntity asEntity(WorkspaceSiteMeta siteMeta);

    WorkspaceSiteMeta asDomain(WorkspaceSiteMetaEntity siteMetaEntity);
}
