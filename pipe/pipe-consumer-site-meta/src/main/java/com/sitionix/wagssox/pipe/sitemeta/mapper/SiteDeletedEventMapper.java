package com.sitionix.wagssox.pipe.sitemeta.mapper;

import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import java.util.UUID;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SiteDeletedEventMapper extends EventMapper<SiteDeletedEvent, UUID> {

    @Override
    default Class<SiteDeletedEvent> payloadType() {
        return SiteDeletedEvent.class;
    }

    @Override
    default Class<UUID> resultType() {
        return UUID.class;
    }

    @Override
    default UUID asProjection(final SiteDeletedEvent payload) {
        return UUID.fromString(payload.getSiteId().toString());
    }
}
