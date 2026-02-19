package com.sitionix.wagssox.domain;

import java.time.Instant;
import java.util.UUID;

public record SiteMetaDelete(
        UUID siteId,
        Long userId,
        Instant deletedAt
) {
}
