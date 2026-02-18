package com.sitionix.wagssox.api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record WorkspaceSiteMetaDTO(
        UUID siteId,
        Long ownerUserId,
        String name,
        String status,
        String type,
        String description,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
