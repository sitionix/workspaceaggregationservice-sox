package com.sitionix.wagssox.api.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface WorkspaceSiteMetaStatusApiMapper {

    @Named("asStatusName")
    default String asStatusName(final WorkspaceSiteMetaStatus value) {
        if (value == null) {
            return null;
        }
        return value.name();
    }
}
