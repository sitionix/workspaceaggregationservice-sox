package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.WorkspaceSiteCardDTO;
import com.app_afesox.wagssox.api_first.dto.WorkspaceSitesPageDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                InstantApiMapper.class,
                WorkspaceSiteStatusApiMapper.class,
                WorkspaceSiteTypeApiMapper.class
        }
)
public interface WorkspaceSitesApiMapper {

    @Mapping(target = "items", source = "items")
    @Mapping(target = "page", source = "page")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "hasNext", source = "hasNext")
    WorkspaceSitesPageDTO asWorkspaceSitesPageDTO(WorkspaceSitesPage src);

    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapType")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toUtcOffsetDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toUtcOffsetDateTime")
    WorkspaceSiteCardDTO asWorkspaceSiteCardDTO(WorkspaceSiteMeta src);
}
