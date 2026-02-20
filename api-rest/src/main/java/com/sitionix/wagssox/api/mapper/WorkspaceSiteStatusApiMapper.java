package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.WorkspaceSiteCardDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface WorkspaceSiteStatusApiMapper {

    @Named("mapStatus")
    default WorkspaceSiteCardDTO.StatusEnum mapStatus(final WorkspaceSiteMetaStatus status) {
        if (status == null) {
            return null;
        }
        return WorkspaceSiteCardDTO.StatusEnum.valueOf(status.name());
    }
}
