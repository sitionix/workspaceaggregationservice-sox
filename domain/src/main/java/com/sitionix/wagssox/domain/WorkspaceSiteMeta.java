package com.sitionix.wagssox.domain;

import java.time.Instant;
import java.util.Objects;
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

    public static WorkspaceSiteMeta fromUpdate(final SiteMetaUpdate siteMetaUpdate) {
        return new WorkspaceSiteMeta(
                siteMetaUpdate.siteId(),
                siteMetaUpdate.ownerUserId(),
                siteMetaUpdate.name(),
                siteMetaUpdate.status(),
                siteMetaUpdate.type(),
                siteMetaUpdate.description(),
                siteMetaUpdate.updatedAt(),
                siteMetaUpdate.updatedAt()
        );
    }

    public WorkspaceSiteMeta mergeWith(final SiteMetaUpdate siteMetaUpdate) {
        return new WorkspaceSiteMeta(
                this.siteId(),
                this.ownerUserId(),
                Objects.nonNull(siteMetaUpdate.name()) ? siteMetaUpdate.name() : this.name(),
                Objects.nonNull(siteMetaUpdate.status()) ? siteMetaUpdate.status() : this.status(),
                Objects.nonNull(siteMetaUpdate.type()) ? siteMetaUpdate.type() : this.type(),
                Objects.nonNull(siteMetaUpdate.description()) ? siteMetaUpdate.description() : this.description(),
                this.createdAt(),
                siteMetaUpdate.updatedAt()
        );
    }
}
