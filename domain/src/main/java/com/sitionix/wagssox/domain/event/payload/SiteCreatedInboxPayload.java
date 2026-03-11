package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.forge.inbox.core.port.ForgeInboxPayload;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;

public record SiteCreatedInboxPayload(
        WorkspaceSiteMeta siteMeta
) implements ForgeInboxPayload {
}
