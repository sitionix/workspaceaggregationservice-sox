package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.SiteMetaDelete;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import com.sitionix.wagssox.domain.usecase.SiteMetaProjectionCommand;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteMetaProjectionCommandImpl implements SiteMetaProjectionCommand {

    private final WorkspaceSiteMetaRepository workspaceSiteMetaRepository;

    @Override
    public void applySiteCreated(final WorkspaceSiteMeta siteMeta) {
        this.workspaceSiteMetaRepository.save(siteMeta);
    }

    @Override
    public void applySiteUpdated(final SiteMetaUpdate siteMetaUpdate) {
        final WorkspaceSiteMeta nextState = this.workspaceSiteMetaRepository.findBySiteId(siteMetaUpdate.getSiteId())
                .map(existing -> this.withUpdate(existing.toBuilder(), siteMetaUpdate).build())
                .orElseGet(() -> this.withUpdate(
                                WorkspaceSiteMeta.builder()
                                        .siteId(siteMetaUpdate.getSiteId())
                                        .createdAt(siteMetaUpdate.getUpdatedAt()),
                                siteMetaUpdate
                        )
                        .build());

        this.workspaceSiteMetaRepository.save(nextState);
    }

    @Override
    public void applySiteDeleted(final SiteMetaDelete siteMetaDelete) {
        this.workspaceSiteMetaRepository.findBySiteId(siteMetaDelete.getSiteId())
                .ifPresent(existing -> this.workspaceSiteMetaRepository.save(existing.toBuilder()
                        .userId(siteMetaDelete.getUserId() == null ? existing.getUserId() : siteMetaDelete.getUserId())
                        .status(WorkspaceSiteMetaStatus.ARCHIVED)
                        .updatedAt(siteMetaDelete.getDeletedAt())
                        .deletedAt(siteMetaDelete.getDeletedAt())
                        .build()));
    }

    private WorkspaceSiteMeta.WorkspaceSiteMetaBuilder withUpdate(final WorkspaceSiteMeta.WorkspaceSiteMetaBuilder builder,
                                                                  final SiteMetaUpdate siteMetaUpdate) {
        return builder
                .userId(siteMetaUpdate.getUserId())
                .name(siteMetaUpdate.getName())
                .status(siteMetaUpdate.getStatus())
                .type(siteMetaUpdate.getType())
                .description(siteMetaUpdate.getDescription())
                .updatedAt(siteMetaUpdate.getUpdatedAt());
    }
}
