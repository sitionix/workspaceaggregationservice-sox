package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SiteMetaProjectionCommandImplTest {

    @Mock
    private WorkspaceSiteMetaRepository workspaceSiteMetaRepository;

    private SiteMetaProjectionCommandImpl siteMetaProjectionCommand;

    @BeforeEach
    void setUp() {
        this.siteMetaProjectionCommand = new SiteMetaProjectionCommandImpl(this.workspaceSiteMetaRepository);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.workspaceSiteMetaRepository);
    }

    @Test
    void givenSiteMeta_whenApplySiteCreated_thenSaveProjection() {
        //given
        final WorkspaceSiteMeta siteMeta = this.getWorkspaceSiteMeta(
                UUID.fromString("353ef6f8-6b77-45bc-ab22-f3ecf5f8b905"),
                42L,
                "Site A",
                WorkspaceSiteMetaStatus.DRAFT,
                WorkspaceSiteMetaType.PORTFOLIO,
                "Description",
                Instant.parse("2026-02-18T08:00:00Z"),
                Instant.parse("2026-02-18T10:00:00Z")
        );

        //when
        this.siteMetaProjectionCommand.applySiteCreated(siteMeta);

        //then
        verify(this.workspaceSiteMetaRepository).save(siteMeta);
    }

    @Test
    void givenMissingSiteMeta_whenApplySiteUpdated_thenCreateProjectionFromUpdate() {
        //given
        final UUID siteId = UUID.fromString("3db551f5-98f2-4f9a-b4f5-a5e8f2f13fcb");
        final Instant updatedAt = Instant.parse("2026-02-18T11:30:00Z");
        final SiteMetaUpdate siteMetaUpdate = this.getSiteMetaUpdate(
                siteId,
                55L,
                "Projected Site",
                WorkspaceSiteMetaStatus.DRAFT,
                WorkspaceSiteMetaType.BLOG,
                "From update",
                updatedAt
        );
        final WorkspaceSiteMeta expected = this.getWorkspaceSiteMeta(
                siteId,
                55L,
                "Projected Site",
                WorkspaceSiteMetaStatus.DRAFT,
                WorkspaceSiteMetaType.BLOG,
                "From update",
                updatedAt,
                updatedAt
        );
        when(this.workspaceSiteMetaRepository.findBySiteId(siteId)).thenReturn(Optional.empty());

        //when
        this.siteMetaProjectionCommand.applySiteUpdated(siteMetaUpdate);

        //then
        verify(this.workspaceSiteMetaRepository).findBySiteId(siteId);
        verify(this.workspaceSiteMetaRepository).save(expected);
    }

    @Test
    void givenExistingSiteMetaWithSameOwner_whenApplySiteUpdated_thenMergeAndSaveProjection() {
        //given
        final UUID siteId = UUID.fromString("7adf7f5f-a55e-4688-810d-3ca8a4bb9cd4");
        final WorkspaceSiteMeta existing = this.getWorkspaceSiteMeta(
                siteId,
                42L,
                "Old Name",
                WorkspaceSiteMetaStatus.DRAFT,
                WorkspaceSiteMetaType.PORTFOLIO,
                "Old description",
                Instant.parse("2026-02-18T08:00:00Z"),
                Instant.parse("2026-02-18T09:00:00Z")
        );
        final SiteMetaUpdate siteMetaUpdate = this.getSiteMetaUpdate(
                siteId,
                42L,
                "New Name",
                WorkspaceSiteMetaStatus.PUBLISHED,
                null,
                null,
                Instant.parse("2026-02-18T11:00:00Z")
        );
        final WorkspaceSiteMeta expected = this.getWorkspaceSiteMeta(
                siteId,
                42L,
                "New Name",
                WorkspaceSiteMetaStatus.PUBLISHED,
                WorkspaceSiteMetaType.PORTFOLIO,
                "Old description",
                Instant.parse("2026-02-18T08:00:00Z"),
                Instant.parse("2026-02-18T11:00:00Z")
        );
        when(this.workspaceSiteMetaRepository.findBySiteId(siteId)).thenReturn(Optional.of(existing));

        //when
        this.siteMetaProjectionCommand.applySiteUpdated(siteMetaUpdate);

        //then
        verify(this.workspaceSiteMetaRepository).findBySiteId(siteId);
        verify(this.workspaceSiteMetaRepository).save(expected);
    }

    @Test
    void givenExistingSiteMetaWithDifferentOwner_whenApplySiteUpdated_thenSkipProjectionUpdate() {
        //given
        final UUID siteId = UUID.fromString("4559bc29-c6eb-4fdd-89d2-591dfb760f37");
        final WorkspaceSiteMeta existing = this.getWorkspaceSiteMeta(
                siteId,
                42L,
                "Owner Locked",
                WorkspaceSiteMetaStatus.DRAFT,
                WorkspaceSiteMetaType.PORTFOLIO,
                "Description",
                Instant.parse("2026-02-18T08:00:00Z"),
                Instant.parse("2026-02-18T09:30:00Z")
        );
        final SiteMetaUpdate siteMetaUpdate = this.getSiteMetaUpdate(
                siteId,
                999L,
                "Should Not Apply",
                WorkspaceSiteMetaStatus.PUBLISHED,
                WorkspaceSiteMetaType.STORE,
                "Should Not Apply",
                Instant.parse("2026-02-18T12:30:00Z")
        );
        when(this.workspaceSiteMetaRepository.findBySiteId(siteId)).thenReturn(Optional.of(existing));

        //when
        this.siteMetaProjectionCommand.applySiteUpdated(siteMetaUpdate);

        //then
        verify(this.workspaceSiteMetaRepository).findBySiteId(siteId);
    }

    @Test
    void givenSiteId_whenApplySiteDeleted_thenDeleteProjection() {
        //given
        final UUID siteId = UUID.fromString("3600b638-ed8c-47e5-845e-228f7f08a856");

        //when
        this.siteMetaProjectionCommand.applySiteDeleted(siteId);

        //then
        verify(this.workspaceSiteMetaRepository).deleteBySiteId(siteId);
    }

    private WorkspaceSiteMeta getWorkspaceSiteMeta(
            final UUID siteId,
            final Long ownerUserId,
            final String name,
            final WorkspaceSiteMetaStatus status,
            final WorkspaceSiteMetaType type,
            final String description,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new WorkspaceSiteMeta(
                siteId,
                ownerUserId,
                name,
                status,
                type,
                description,
                createdAt,
                updatedAt
        );
    }

    private SiteMetaUpdate getSiteMetaUpdate(
            final UUID siteId,
            final Long ownerUserId,
            final String name,
            final WorkspaceSiteMetaStatus status,
            final WorkspaceSiteMetaType type,
            final String description,
            final Instant updatedAt
    ) {
        return new SiteMetaUpdate(
                siteId,
                ownerUserId,
                name,
                status,
                type,
                description,
                updatedAt
        );
    }
}
