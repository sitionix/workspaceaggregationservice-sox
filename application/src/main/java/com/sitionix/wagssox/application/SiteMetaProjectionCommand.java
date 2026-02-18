package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import java.time.Instant;
import java.util.UUID;

public interface SiteMetaProjectionCommand {

    void applySiteCreated(WorkspaceSiteMeta siteMeta);

    void applySiteUpdated(UUID siteId,
                          Long ownerUserId,
                          String name,
                          WorkspaceSiteMetaStatus status,
                          WorkspaceSiteMetaType type,
                          String description,
                          Instant updatedAt);

    void applySiteDeleted(UUID siteId);
}
