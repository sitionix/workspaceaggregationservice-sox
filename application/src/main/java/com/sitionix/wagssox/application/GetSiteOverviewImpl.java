package com.sitionix.wagssox.application;

import com.sitionix.forge.security.server.user.ForgeUserClient;
import com.sitionix.wagssox.domain.SiteOverview;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.exception.SiteOverviewNotFoundException;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import com.sitionix.wagssox.domain.usecase.GetSiteOverview;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSiteOverviewImpl implements GetSiteOverview {

    private final WorkspaceSiteMetaRepository workspaceSiteMetaRepository;
    private final ForgeUserClient forgeUserClient;

    @Override
    public SiteOverview execute(final UUID siteId) {
        final Long userId = this.forgeUserClient.getUserId();
        final WorkspaceSiteMeta siteMeta = this.workspaceSiteMetaRepository.findActiveByUserIdAndSiteId(userId, siteId)
                .orElseThrow(() -> new SiteOverviewNotFoundException("Site not found"));
        return this.asSiteOverview(siteMeta);
    }

    private SiteOverview asSiteOverview(final WorkspaceSiteMeta siteMeta) {
        return SiteOverview.builder()
                .siteId(siteMeta.getSiteId())
                .name(siteMeta.getName())
                .status(siteMeta.getStatus())
                .type(siteMeta.getType())
                .description(siteMeta.getDescription())
                .createdAt(siteMeta.getCreatedAt())
                .updatedAt(siteMeta.getUpdatedAt())
                .build();
    }
}
