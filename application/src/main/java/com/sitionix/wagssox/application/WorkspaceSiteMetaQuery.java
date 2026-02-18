package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import java.util.List;

public interface WorkspaceSiteMetaQuery {

    List<WorkspaceSiteMeta> findByOwnerUserId(Long ownerUserId);
}
