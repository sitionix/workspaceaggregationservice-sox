package com.sitionix.wagssox.api.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface WorkspaceSiteMetaTypeApiMapper {

    @Named("asTypeName")
    default String asTypeName(final WorkspaceSiteMetaType value) {
        if (value == null) {
            return null;
        }
        return value.name();
    }
}
