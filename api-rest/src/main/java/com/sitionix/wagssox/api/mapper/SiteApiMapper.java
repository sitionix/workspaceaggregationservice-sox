package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.SiteOverviewDTO;
import com.app_afesox.wagssox.api_first.dto.WorkspaceSiteCardDTO;
import com.app_afesox.wagssox.api_first.dto.WorkspaceSitesPageDTO;
import com.sitionix.wagssox.domain.SiteOverview;
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
                SiteOverviewStatusApiMapper.class,
                SiteOverviewTypeApiMapper.class,
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

    @Mapping(target = "status", source = "status", qualifiedByName = "mapSiteOverviewStatus")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapSiteOverviewType")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toUtcOffsetDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toUtcOffsetDateTime")
    SiteOverviewDTO asSiteOverviewDTO(SiteOverview src);

    @Named("toUtcOffsetDateTime")
    default OffsetDateTime toUtcOffsetDateTime(final Instant src) {
        if (src == null) {
            return null;
        }
        return src.atOffset(ZoneOffset.UTC);
    }
}
