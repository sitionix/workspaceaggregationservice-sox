package com.sitionix.wagssox.domain;

import java.time.Instant;
import java.util.UUID;

public record WorkspaceSiteMeta(
        UUID siteId,
        Long ownerUserId,
        String name,
        WorkspaceSiteMetaStatus status,
        WorkspaceSiteMetaType type,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
