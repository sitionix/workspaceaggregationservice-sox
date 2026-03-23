package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.SiteOverviewDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SiteOverviewStatusApiMapper {

    @Named("mapSiteOverviewStatus")
    default SiteOverviewDTO.StatusEnum mapStatus(final WorkspaceSiteMetaStatus status) {
        if (status == null) {
            return null;
        }
        return SiteOverviewDTO.StatusEnum.valueOf(status.name());
    }
}
