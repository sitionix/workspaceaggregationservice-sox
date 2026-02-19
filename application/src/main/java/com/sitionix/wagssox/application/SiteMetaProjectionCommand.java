package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import java.util.UUID;

public interface SiteMetaProjectionCommand {

    void applySiteCreated(WorkspaceSiteMeta siteMeta);

    void applySiteUpdated(SiteMetaUpdate siteMetaUpdate);

    void applySiteDeleted(UUID siteId);
}
