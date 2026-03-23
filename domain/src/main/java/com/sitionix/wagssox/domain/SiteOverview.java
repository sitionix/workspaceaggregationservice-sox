package com.sitionix.wagssox.domain;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SiteOverview(
        UUID siteId,
        String name,
        WorkspaceSiteMetaStatus status,
        WorkspaceSiteMetaType type,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
