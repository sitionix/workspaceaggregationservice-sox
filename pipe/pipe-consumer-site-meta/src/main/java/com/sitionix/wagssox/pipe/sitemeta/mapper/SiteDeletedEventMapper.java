package com.sitionix.wagssox.pipe.sitemeta.mapper;

import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.sitionix.wagssox.domain.SiteMetaDelete;
import java.time.Instant;
import java.util.UUID;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SiteDeletedEventMapper extends EventMapper<SiteDeletedEvent, SiteMetaDelete> {

    @Override
    default Class<SiteDeletedEvent> payloadType() {
        return SiteDeletedEvent.class;
    }

    @Override
    default Class<SiteMetaDelete> resultType() {
        return SiteMetaDelete.class;
    }

    @Override
    default SiteMetaDelete asProjection(final SiteDeletedEvent payload) {
        return new SiteMetaDelete(
                payload.getSiteId() == null ? null : UUID.fromString(payload.getSiteId().toString()),
                payload.getUserId(),
                payload.getDeletedAt() == null ? null : Instant.parse(payload.getDeletedAt().toString())
        );
    }
}
