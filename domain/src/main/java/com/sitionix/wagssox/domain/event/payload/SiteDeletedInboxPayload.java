package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.wagssox.domain.SiteMetaDelete;
import com.sitionix.wagssox.domain.event.SiteMetaEventType;

public record SiteDeletedInboxPayload(
        SiteMetaDelete siteMetaDelete,
        String idempotencyKey
) implements SiteMetaInboxPayload {

    @Override
    public String eventType() {
        return SiteMetaEventType.SITE_DELETED.getValue();
    }

    @Override
    public Long aggregateId() {
        return SiteMetaInboxPayload.resolveUserAggregateId(this.siteMetaDelete);
    }
}
