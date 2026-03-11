package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.forge.inbox.core.port.ForgeInboxPayload;
import com.sitionix.wagssox.domain.SiteMetaDelete;

public record SiteDeletedInboxPayload(
        SiteMetaDelete siteMetaDelete
) implements ForgeInboxPayload {
}
