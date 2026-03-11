package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.wagssox.domain.SiteMetaDelete;

public record SiteDeletedInboxPayload(
        SiteMetaDelete siteMetaDelete
) implements SiteMetaInboxPayload {

    @Override
    public Long aggregateId() {
        return SiteMetaInboxPayload.resolveUserAggregateId(this.siteMetaDelete);
    }
}
