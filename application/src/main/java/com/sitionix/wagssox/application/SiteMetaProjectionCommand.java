package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.SiteMetaDelete;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;

public interface SiteMetaProjectionCommand {

    void applySiteCreated(WorkspaceSiteMeta siteMeta);

    void applySiteUpdated(SiteMetaUpdate siteMetaUpdate);

    void applySiteDeleted(SiteMetaDelete siteMetaDelete);
}
