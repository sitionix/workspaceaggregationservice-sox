package com.sitionix.wagssox.domain.usecase;

import com.sitionix.wagssox.domain.WorkspaceSitesPage;

/**
 * Application-facing contract for loading workspace sites for a user.
 */
public interface GetWorkspaceSites {

    /**
     * Loads paginated workspace sites.
     *
     * @param page zero-based page number.
     * @param size requested page size.
     * @return workspace sites page.
     */
    WorkspaceSitesPage execute(Integer page, Integer size);
}
