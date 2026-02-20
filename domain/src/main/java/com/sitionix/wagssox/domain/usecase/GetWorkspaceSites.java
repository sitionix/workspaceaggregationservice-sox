package com.sitionix.wagssox.domain.usecase;

import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import org.springframework.data.domain.Pageable;

/**
 * Application-facing contract for loading workspace sites for a user.
 */
public interface GetWorkspaceSites {

    /**
     * Loads paginated workspace sites.
     *
     * @param pageable page request with paging and sorting.
     * @return workspace sites page.
     */
    WorkspaceSitesPage execute(Pageable pageable);
}
