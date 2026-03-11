package com.sitionix.wagssox.pipe.sitemeta.mapper;

import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SiteCreatedEventMapper extends EventMapper<SiteCreatedEvent, WorkspaceSiteMeta> {

    @Override
    default Class<SiteCreatedEvent> payloadType() {
        return SiteCreatedEvent.class;
    }

    @Override
    default Class<WorkspaceSiteMeta> resultType() {
        return WorkspaceSiteMeta.class;
    }

    @Override
    @Mapping(target = "siteId", expression = "java(java.util.UUID.fromString(payload.getSiteId().toString()))")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "name", expression = "java(payload.getName() == null ? null : payload.getName().toString())")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "description", expression = "java(payload.getDescription() == null ? null : payload.getDescription().toString())")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.parse(payload.getCreatedAt().toString()))")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.parse(payload.getUpdatedAt().toString()))")
    @Mapping(target = "deletedAt", ignore = true)
    WorkspaceSiteMeta asProjection(SiteCreatedEvent payload);
}
