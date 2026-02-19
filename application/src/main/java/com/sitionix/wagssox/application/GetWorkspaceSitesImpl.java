package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import com.sitionix.wagssox.domain.usecase.GetWorkspaceSites;
import com.sitionix.forge.security.server.user.ForgeUserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkspaceSitesImpl implements GetWorkspaceSites {

    private final WorkspaceSiteMetaRepository workspaceSiteMetaRepository;
    private final ForgeUserClient forgeUserClient;

    @Override
    public WorkspaceSitesPage execute(final Integer page, final Integer size) {
        final Long userId = this.forgeUserClient.getUserId();
        return this.workspaceSiteMetaRepository.findActiveByUserId(userId, page, size);
    }
}
