package com.sitionix.wagssox.domain;

import java.time.Instant;
import java.util.UUID;

public record SiteMetaUpdate(
        UUID siteId,
        Long ownerUserId,
        String name,
        WorkspaceSiteMetaStatus status,
        WorkspaceSiteMetaType type,
        String description,
        Instant updatedAt
) {
}
