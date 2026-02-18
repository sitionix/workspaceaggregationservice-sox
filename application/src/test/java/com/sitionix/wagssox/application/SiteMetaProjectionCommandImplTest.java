package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SiteMetaProjectionCommandImplTest {

    private InMemoryWorkspaceSiteMetaRepository inMemoryWorkspaceSiteMetaRepository;
    private SiteMetaProjectionCommandImpl siteMetaProjectionCommand;

    @BeforeEach
    void setUp() {
        this.inMemoryWorkspaceSiteMetaRepository = new InMemoryWorkspaceSiteMetaRepository();
        this.siteMetaProjectionCommand = new SiteMetaProjectionCommandImpl(this.inMemoryWorkspaceSiteMetaRepository);
    }

    @Test
    void givenSiteMeta_whenApplySiteCreated_thenSaveProjection() {
        //given
        final WorkspaceSiteMeta siteMeta = this.getWorkspaceSiteMeta("Site A", Instant.parse("2026-02-18T10:00:00Z"));

        //when
        this.siteMetaProjectionCommand.applySiteCreated(siteMeta);

        //then
        final Optional<WorkspaceSiteMeta> actual = this.inMemoryWorkspaceSiteMetaRepository.findBySiteId(siteMeta.siteId());
        assertThat(actual).contains(siteMeta);
    }

    @Test
    void givenExistingSiteMeta_whenApplySiteUpdated_thenMergeAndSaveProjection() {
        //given
        final UUID siteId = UUID.randomUUID();
        final WorkspaceSiteMeta existing = this.getWorkspaceSiteMeta(siteId, "Old Name", Instant.parse("2026-02-18T09:00:00Z"));
        final Instant updatedAt = Instant.parse("2026-02-18T11:00:00Z");
        this.inMemoryWorkspaceSiteMetaRepository.save(existing);

        //when
        this.siteMetaProjectionCommand.applySiteUpdated(
                siteId,
                existing.ownerUserId(),
                "New Name",
                WorkspaceSiteMetaStatus.PUBLISHED,
                null,
                null,
                updatedAt
        );

        //then
        final WorkspaceSiteMeta actual = this.inMemoryWorkspaceSiteMetaRepository.findBySiteId(siteId).orElseThrow();
        assertThat(actual.siteId()).isEqualTo(siteId);
        assertThat(actual.ownerUserId()).isEqualTo(existing.ownerUserId());
        assertThat(actual.name()).isEqualTo("New Name");
        assertThat(actual.status()).isEqualTo(WorkspaceSiteMetaStatus.PUBLISHED);
        assertThat(actual.type()).isEqualTo(existing.type());
        assertThat(actual.description()).isEqualTo(existing.description());
        assertThat(actual.createdAt()).isEqualTo(existing.createdAt());
        assertThat(actual.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void givenMissingSiteMeta_whenApplySiteUpdated_thenCreateProjectionFromUpdate() {
        //given
        final UUID siteId = UUID.randomUUID();
        final Long ownerUserId = 55L;
        final Instant updatedAt = Instant.parse("2026-02-18T11:30:00Z");

        //when
        this.siteMetaProjectionCommand.applySiteUpdated(
                siteId,
                ownerUserId,
                "Projected Site",
                WorkspaceSiteMetaStatus.DRAFT,
                WorkspaceSiteMetaType.BLOG,
                "From update",
                updatedAt
        );

        //then
        final WorkspaceSiteMeta actual = this.inMemoryWorkspaceSiteMetaRepository.findBySiteId(siteId).orElseThrow();
        assertThat(actual.siteId()).isEqualTo(siteId);
        assertThat(actual.ownerUserId()).isEqualTo(ownerUserId);
        assertThat(actual.name()).isEqualTo("Projected Site");
        assertThat(actual.status()).isEqualTo(WorkspaceSiteMetaStatus.DRAFT);
        assertThat(actual.type()).isEqualTo(WorkspaceSiteMetaType.BLOG);
        assertThat(actual.description()).isEqualTo("From update");
        assertThat(actual.createdAt()).isEqualTo(updatedAt);
        assertThat(actual.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void givenSiteId_whenApplySiteDeleted_thenDeleteProjection() {
        //given
        final WorkspaceSiteMeta siteMeta = this.getWorkspaceSiteMeta("Delete me", Instant.parse("2026-02-18T12:10:00Z"));
        this.inMemoryWorkspaceSiteMetaRepository.save(siteMeta);

        //when
        this.siteMetaProjectionCommand.applySiteDeleted(siteMeta.siteId());

        //then
        assertThat(this.inMemoryWorkspaceSiteMetaRepository.findBySiteId(siteMeta.siteId())).isEmpty();
    }

    private WorkspaceSiteMeta getWorkspaceSiteMeta(final String name, final Instant updatedAt) {
        return this.getWorkspaceSiteMeta(UUID.randomUUID(), name, updatedAt);
    }

    private WorkspaceSiteMeta getWorkspaceSiteMeta(final UUID siteId, final String name, final Instant updatedAt) {
        return new WorkspaceSiteMeta(
                siteId,
                42L,
                name,
                WorkspaceSiteMetaStatus.DRAFT,
                WorkspaceSiteMetaType.PORTFOLIO,
                "Description",
                Instant.parse("2026-02-18T08:00:00Z"),
                updatedAt
        );
    }

    private static class InMemoryWorkspaceSiteMetaRepository implements WorkspaceSiteMetaRepository {

        private final Map<UUID, WorkspaceSiteMeta> storage = new HashMap<>();

        @Override
        public WorkspaceSiteMeta save(final WorkspaceSiteMeta siteMeta) {
            this.storage.put(siteMeta.siteId(), siteMeta);
            return siteMeta;
        }

        @Override
        public Optional<WorkspaceSiteMeta> findBySiteId(final UUID siteId) {
            return Optional.ofNullable(this.storage.get(siteId));
        }

        @Override
        public List<WorkspaceSiteMeta> findByOwnerUserId(final Long ownerUserId) {
            final List<WorkspaceSiteMeta> siteMetas = new ArrayList<>();
            for (WorkspaceSiteMeta siteMeta : this.storage.values()) {
                if (siteMeta.ownerUserId().equals(ownerUserId)) {
                    siteMetas.add(siteMeta);
                }
            }
            return siteMetas;
        }

        @Override
        public void deleteBySiteId(final UUID siteId) {
            this.storage.remove(siteId);
        }
    }
}
