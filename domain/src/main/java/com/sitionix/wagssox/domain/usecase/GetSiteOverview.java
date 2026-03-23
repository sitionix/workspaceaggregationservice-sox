package com.sitionix.wagssox.domain.usecase;

import com.sitionix.wagssox.domain.SiteOverview;
import java.util.UUID;

/**
 * Application-facing contract for loading overview data for a single site.
 */
public interface GetSiteOverview {

    /**
     * Loads overview data for a single site accessible to the current user.
     *
     * @param siteId site identifier.
     * @return site overview data.
     */
    SiteOverview execute(UUID siteId);
}
