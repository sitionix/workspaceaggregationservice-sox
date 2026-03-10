package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.event.SiteMetaEventType;

public record SiteUpdatedInboxPayload(
        SiteMetaUpdate siteMetaUpdate,
        String idempotencyKey
) implements SiteMetaInboxPayload {

    @Override
    public String eventType() {
        return SiteMetaEventType.SITE_UPDATED.getValue();
    }

    @Override
    public Long aggregateId() {
        return SiteMetaInboxPayload.resolveUserAggregateId(this.siteMetaUpdate);
    }
}
