package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import java.util.Optional;
import java.util.UUID;
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
        final Optional<WorkspaceSiteMeta> existingSiteMeta = this.workspaceSiteMetaRepository.findBySiteId(siteMetaUpdate.siteId());
        existingSiteMeta.ifPresentOrElse(
                existing -> this.workspaceSiteMetaRepository.save(this.mergedProjectionState(existing, siteMetaUpdate)),
                () -> this.workspaceSiteMetaRepository.save(this.newProjectionState(siteMetaUpdate))
        );
    }

    @Override
    public void applySiteDeleted(final UUID siteId) {
        this.workspaceSiteMetaRepository.deleteBySiteId(siteId);
    }

    private WorkspaceSiteMeta mergedProjectionState(final WorkspaceSiteMeta existing, final SiteMetaUpdate siteMetaUpdate) {
        return new WorkspaceSiteMeta(
                existing.siteId(),
                siteMetaUpdate.userId(),
                siteMetaUpdate.name(),
                siteMetaUpdate.status(),
                siteMetaUpdate.type(),
                siteMetaUpdate.description(),
                existing.createdAt(),
                siteMetaUpdate.updatedAt()
        );
    }

    private WorkspaceSiteMeta newProjectionState(final SiteMetaUpdate siteMetaUpdate) {
        return new WorkspaceSiteMeta(
                siteMetaUpdate.siteId(),
                siteMetaUpdate.userId(),
                siteMetaUpdate.name(),
                siteMetaUpdate.status(),
                siteMetaUpdate.type(),
                siteMetaUpdate.description(),
                siteMetaUpdate.updatedAt(),
                siteMetaUpdate.updatedAt()
        );
    }
}
