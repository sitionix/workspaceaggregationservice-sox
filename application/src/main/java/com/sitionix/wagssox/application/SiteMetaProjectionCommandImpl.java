package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import java.util.Objects;
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
        this.workspaceSiteMetaRepository.findBySiteId(siteMetaUpdate.siteId())
                .ifPresent(existing -> this.saveMergedIfUserMatches(existing, siteMetaUpdate));
    }

    @Override
    public void applySiteDeleted(final UUID siteId) {
        this.workspaceSiteMetaRepository.deleteBySiteId(siteId);
    }

    private void saveMergedIfUserMatches(final WorkspaceSiteMeta existing, final SiteMetaUpdate siteMetaUpdate) {
        if (Objects.nonNull(siteMetaUpdate.userId())
                && !Objects.equals(existing.userId(), siteMetaUpdate.userId())) {
            return;
        }
        this.workspaceSiteMetaRepository.save(this.mergedProjectionState(existing, siteMetaUpdate));
    }

    private WorkspaceSiteMeta mergedProjectionState(final WorkspaceSiteMeta existing, final SiteMetaUpdate siteMetaUpdate) {
        return new WorkspaceSiteMeta(
                existing.siteId(),
                existing.userId(),
                Objects.nonNull(siteMetaUpdate.name()) ? siteMetaUpdate.name() : existing.name(),
                Objects.nonNull(siteMetaUpdate.status()) ? siteMetaUpdate.status() : existing.status(),
                Objects.nonNull(siteMetaUpdate.type()) ? siteMetaUpdate.type() : existing.type(),
                Objects.nonNull(siteMetaUpdate.description()) ? siteMetaUpdate.description() : existing.description(),
                existing.createdAt(),
                siteMetaUpdate.updatedAt()
        );
    }
}
