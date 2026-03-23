package com.sitionix.wagssox.api.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InstantApiMapper {

    @Named("toUtcOffsetDateTime")
    default OffsetDateTime toUtcOffsetDateTime(final Instant src) {
        if (src == null) {
            return null;
        }
        return src.atOffset(ZoneOffset.UTC);
    }
}
