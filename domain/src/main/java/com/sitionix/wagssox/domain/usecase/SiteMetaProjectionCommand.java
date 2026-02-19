package com.sitionix.wagssox.domain.usecase;

import com.sitionix.wagssox.domain.SiteMetaDelete;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;

/**
 * Application-facing contract for mutating workspace site metadata projection state.
 */
public interface SiteMetaProjectionCommand {

    /**
     * Applies a create-site projection event.
     *
     * @param siteMeta projection state to persist.
     */
    void applySiteCreated(WorkspaceSiteMeta siteMeta);

    /**
     * Applies an update-site projection event.
     *
     * @param siteMetaUpdate partial update payload.
     */
    void applySiteUpdated(SiteMetaUpdate siteMetaUpdate);

    /**
     * Applies a delete-site projection event.
     *
     * @param siteMetaDelete delete payload.
     */
    void applySiteDeleted(SiteMetaDelete siteMetaDelete);
}
