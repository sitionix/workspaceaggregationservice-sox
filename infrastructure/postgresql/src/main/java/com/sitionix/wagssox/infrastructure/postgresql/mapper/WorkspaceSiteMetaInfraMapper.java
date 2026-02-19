package com.sitionix.wagssox.infrastructure.postgresql.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import org.springframework.data.domain.Page;
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

    default WorkspaceSitesPage asWorkspaceSitesPage(final Page<WorkspaceSiteMetaEntity> entities) {
        return WorkspaceSitesPage.builder()
                .items(entities.getContent().stream()
                        .map(this::asDomain)
                        .map(this::applyNameFallback)
                        .toList())
                .page(entities.getNumber())
                .size(entities.getSize())
                .hasNext(entities.hasNext())
                .build();
    }

    private WorkspaceSiteMeta applyNameFallback(final WorkspaceSiteMeta siteMeta) {
        if (siteMeta.getName() == null) {
            return siteMeta.toBuilder()
                    .name("Untitled site")
                    .build();
        }
        return siteMeta;
    }
}
