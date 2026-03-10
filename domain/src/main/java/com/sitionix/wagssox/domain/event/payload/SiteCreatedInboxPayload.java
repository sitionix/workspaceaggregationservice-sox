package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.event.SiteMetaEventType;

public record SiteCreatedInboxPayload(
        WorkspaceSiteMeta siteMeta,
        String idempotencyKey
) implements SiteMetaInboxPayload {

    @Override
    public String eventType() {
        return SiteMetaEventType.SITE_CREATED.getValue();
    }

    @Override
    public Long aggregateId() {
        return SiteMetaInboxPayload.resolveUserAggregateId(this.siteMeta);
    }
}
