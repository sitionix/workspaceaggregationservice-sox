package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import java.time.Instant;
import java.util.Objects;
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
        final UUID siteId = siteMetaUpdate.siteId();
        final Long ownerUserId = siteMetaUpdate.ownerUserId();
        final String name = siteMetaUpdate.name();
        final WorkspaceSiteMetaStatus status = siteMetaUpdate.status();
        final WorkspaceSiteMetaType type = siteMetaUpdate.type();
        final String description = siteMetaUpdate.description();
        final Instant updatedAt = siteMetaUpdate.updatedAt();
        final Optional<WorkspaceSiteMeta> maybeExisting = this.workspaceSiteMetaRepository.findBySiteId(siteId);

        if (maybeExisting.isEmpty()) {
            final WorkspaceSiteMeta createdFromUpdate = new WorkspaceSiteMeta(
                    siteId,
                    ownerUserId,
                    name,
                    status,
                    type,
                    description,
                    updatedAt,
                    updatedAt
            );
            this.workspaceSiteMetaRepository.save(createdFromUpdate);
            return;
        }

        final WorkspaceSiteMeta existing = maybeExisting.get();
        if (Objects.nonNull(ownerUserId) && !Objects.equals(existing.ownerUserId(), ownerUserId)) {
            return;
        }

        final WorkspaceSiteMeta updatedSiteMeta = new WorkspaceSiteMeta(
                existing.siteId(),
                existing.ownerUserId(),
                Objects.nonNull(name) ? name : existing.name(),
                Objects.nonNull(status) ? status : existing.status(),
                Objects.nonNull(type) ? type : existing.type(),
                Objects.nonNull(description) ? description : existing.description(),
                existing.createdAt(),
                updatedAt
        );
        this.workspaceSiteMetaRepository.save(updatedSiteMeta);
    }

    @Override
    public void applySiteDeleted(final UUID siteId) {
        this.workspaceSiteMetaRepository.deleteBySiteId(siteId);
    }
}
