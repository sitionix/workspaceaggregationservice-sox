package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.SiteOverviewDTO;
import com.sitionix.wagssox.domain.SiteOverview;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                InstantApiMapper.class,
                SiteOverviewStatusApiMapper.class,
                SiteOverviewTypeApiMapper.class
        }
)
public interface SiteOverviewApiMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "mapSiteOverviewStatus")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapSiteOverviewType")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toUtcOffsetDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toUtcOffsetDateTime")
    SiteOverviewDTO asSiteOverviewDTO(SiteOverview src);
}
