package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.WorkspaceSiteCardDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface WorkspaceSiteTypeApiMapper {

    @Named("mapType")
    default WorkspaceSiteCardDTO.TypeEnum mapType(final WorkspaceSiteMetaType type) {
        if (type == null) {
            return null;
        }
        return WorkspaceSiteCardDTO.TypeEnum.valueOf(type.name());
    }
}
