package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.SiteOverviewDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SiteOverviewTypeApiMapper {

    @Named("mapSiteOverviewType")
    default SiteOverviewDTO.TypeEnum mapType(final WorkspaceSiteMetaType type) {
        if (type == null) {
            return null;
        }
        return SiteOverviewDTO.TypeEnum.valueOf(type.name());
    }
}
