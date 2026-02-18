package com.sitionix.wagssox.pipe.sitemeta.mapper;

import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteStatusDTO;
import com.app_afesox.stsssox.events.sitemeta.SiteTypeDTO;
import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SiteMetaEventMapper {

    public WorkspaceSiteMeta asSiteMeta(final SiteCreatedEvent event) {
        return new WorkspaceSiteMeta(
                UUID.fromString(event.getSiteId().toString()),
                event.getOwnerUserId(),
                event.getName().toString(),
                this.asStatus(event.getStatus()),
                this.asType(event.getType()),
                this.asNullableString(event.getDescription()),
                Instant.parse(event.getCreatedAt().toString()),
                Instant.parse(event.getUpdatedAt().toString())
        );
    }

    public UUID asSiteId(final SiteUpdatedEvent event) {
        return UUID.fromString(event.getSiteId().toString());
    }

    public Long asOwnerUserId(final SiteUpdatedEvent event) {
        return event.getOwnerUserId();
    }

    public String asName(final SiteUpdatedEvent event) {
        return this.asNullableString(event.getName());
    }

    public WorkspaceSiteMetaStatus asStatus(final SiteUpdatedEvent event) {
        return this.asStatus(event.getStatus());
    }

    public WorkspaceSiteMetaType asType(final SiteUpdatedEvent event) {
        return this.asType(event.getType());
    }

    public String asDescription(final SiteUpdatedEvent event) {
        return this.asNullableString(event.getDescription());
    }

    public Instant asUpdatedAt(final SiteUpdatedEvent event) {
        return Instant.parse(event.getUpdatedAt().toString());
    }

    public UUID asSiteId(final SiteDeletedEvent event) {
        return UUID.fromString(event.getSiteId().toString());
    }

    private WorkspaceSiteMetaStatus asStatus(final SiteStatusDTO status) {
        if (status == null) {
            return null;
        }
        return WorkspaceSiteMetaStatus.valueOf(status.name());
    }

    private WorkspaceSiteMetaType asType(final SiteTypeDTO type) {
        if (type == null) {
            return null;
        }
        return WorkspaceSiteMetaType.valueOf(type.name());
    }

    private String asNullableString(final CharSequence value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
