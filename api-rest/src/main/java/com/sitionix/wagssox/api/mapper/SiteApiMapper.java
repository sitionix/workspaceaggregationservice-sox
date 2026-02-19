package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.WorkspaceSiteCardDTO;
import com.app_afesox.wagssox.api_first.dto.WorkspaceSitesPageDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                WorkspaceSiteStatusApiMapper.class,
                WorkspaceSiteTypeApiMapper.class
        }
)
public interface SiteApiMapper {

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

    @Named("toUtcOffsetDateTime")
    default OffsetDateTime toUtcOffsetDateTime(final Instant src) {
        if (src == null) {
            return null;
        }
        return src.atOffset(ZoneOffset.UTC);
    }
}
