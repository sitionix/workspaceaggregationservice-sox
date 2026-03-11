package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;

public record SiteCreatedInboxPayload(
        WorkspaceSiteMeta siteMeta
) implements SiteMetaInboxPayload {

    @Override
    public Long aggregateId() {
        return SiteMetaInboxPayload.resolveUserAggregateId(this.siteMeta);
    }
}
