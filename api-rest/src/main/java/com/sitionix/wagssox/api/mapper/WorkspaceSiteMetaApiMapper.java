package com.sitionix.wagssox.api.mapper;

import com.sitionix.wagssox.api.dto.WorkspaceSiteMetaDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                WorkspaceSiteMetaStatusApiMapper.class,
                WorkspaceSiteMetaTypeApiMapper.class
        }
)
public interface WorkspaceSiteMetaApiMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "asStatusName")
    @Mapping(target = "type", source = "type", qualifiedByName = "asTypeName")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "asUtcOffsetDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "asUtcOffsetDateTime")
    WorkspaceSiteMetaDTO asDto(WorkspaceSiteMeta workspaceSiteMeta);

    List<WorkspaceSiteMetaDTO> asDtoList(List<WorkspaceSiteMeta> workspaceSiteMetas);

    @Named("asUtcOffsetDateTime")
    default OffsetDateTime asUtcOffsetDateTime(final Instant instant) {
        if (instant == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
