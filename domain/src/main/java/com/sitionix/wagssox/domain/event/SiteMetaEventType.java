package com.sitionix.wagssox.domain.event;

import com.sitionix.forge.inbox.core.model.ForgeInboxEventType;
import com.sitionix.forge.inbox.core.model.ForgeInboxTypedEnum;
import com.sitionix.forge.inbox.core.port.ForgeInboxPayload;
import com.sitionix.wagssox.domain.event.payload.SiteCreatedInboxPayload;
import com.sitionix.wagssox.domain.event.payload.SiteDeletedInboxPayload;
import com.sitionix.wagssox.domain.event.payload.SiteUpdatedInboxPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SiteMetaEventType implements ForgeInboxEventType {

    SITE_CREATED(1L, "SITE_CREATED", SiteCreatedInboxPayload.class),
    SITE_UPDATED(2L, "SITE_UPDATED", SiteUpdatedInboxPayload.class),
    SITE_DELETED(3L, "SITE_DELETED", SiteDeletedInboxPayload.class);

    private final Long id;
    private final String description;
    private final Class<? extends ForgeInboxPayload> payloadClass;

    @Override
    public Class<? extends ForgeInboxPayload> payloadClass() {
        return this.payloadClass;
    }

    public static SiteMetaEventType fromId(final Long id) {
        return ForgeInboxTypedEnum.fromId(SiteMetaEventType.class, id);
    }

    public static SiteMetaEventType fromDescription(final String description) {
        return ForgeInboxTypedEnum.fromDescription(SiteMetaEventType.class, description);
    }
}
