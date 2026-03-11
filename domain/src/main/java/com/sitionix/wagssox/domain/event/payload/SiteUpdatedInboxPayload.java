package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.forge.inbox.core.port.ForgeInboxPayload;
import com.sitionix.wagssox.domain.SiteMetaUpdate;

public record SiteUpdatedInboxPayload(
        SiteMetaUpdate siteMetaUpdate
) implements ForgeInboxPayload {
}
