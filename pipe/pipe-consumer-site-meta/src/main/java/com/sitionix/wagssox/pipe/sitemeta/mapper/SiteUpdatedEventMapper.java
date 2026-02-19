package com.sitionix.wagssox.pipe.sitemeta.mapper;

import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SiteUpdatedEventMapper extends EventMapper<SiteUpdatedEvent, SiteMetaUpdate> {

    @Override
    default Class<SiteUpdatedEvent> payloadType() {
        return SiteUpdatedEvent.class;
    }

    @Override
    default Class<SiteMetaUpdate> resultType() {
        return SiteMetaUpdate.class;
    }

    @Override
    @Mapping(target = "siteId", expression = "java(java.util.UUID.fromString(payload.getSiteId().toString()))")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "name", expression = "java(payload.getName() == null ? null : payload.getName().toString())")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "description", expression = "java(payload.getDescription() == null ? null : payload.getDescription().toString())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.parse(payload.getUpdatedAt().toString()))")
    SiteMetaUpdate asProjection(SiteUpdatedEvent payload);
}
