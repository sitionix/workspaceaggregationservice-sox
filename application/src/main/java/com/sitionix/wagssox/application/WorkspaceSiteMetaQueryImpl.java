package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkspaceSiteMetaQueryImpl implements WorkspaceSiteMetaQuery {

    private final WorkspaceSiteMetaRepository workspaceSiteMetaRepository;

    @Override
    public List<WorkspaceSiteMeta> findByOwnerUserId(final Long ownerUserId) {
        return this.workspaceSiteMetaRepository.findByOwnerUserId(ownerUserId);
    }
}
