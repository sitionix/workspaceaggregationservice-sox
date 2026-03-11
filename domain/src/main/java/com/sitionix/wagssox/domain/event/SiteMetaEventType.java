package com.sitionix.wagssox.domain.event;

import com.sitionix.forge.inbox.core.model.ForgeInboxTypedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SiteMetaEventType implements ForgeInboxTypedEnum {

    SITE_CREATED(1L, "SITE_CREATED"),
    SITE_UPDATED(2L, "SITE_UPDATED"),
    SITE_DELETED(3L, "SITE_DELETED");

    private final Long id;
    private final String description;

    public static SiteMetaEventType fromId(final Long id) {
        return ForgeInboxTypedEnum.fromId(SiteMetaEventType.class, id);
    }

    public static SiteMetaEventType fromDescription(final String description) {
        return ForgeInboxTypedEnum.fromDescription(SiteMetaEventType.class, description);
    }
}
