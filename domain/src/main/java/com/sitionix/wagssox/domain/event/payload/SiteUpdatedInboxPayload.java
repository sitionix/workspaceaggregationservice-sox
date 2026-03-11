package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.wagssox.domain.SiteMetaUpdate;

public record SiteUpdatedInboxPayload(
        SiteMetaUpdate siteMetaUpdate
) implements SiteMetaInboxPayload {

    @Override
    public Long aggregateId() {
        return SiteMetaInboxPayload.resolveUserAggregateId(this.siteMetaUpdate);
    }
}
