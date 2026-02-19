package com.sitionix.wagssox.pipe.sitemeta;

import com.app_afesox.events.Metadata;
import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteMetaEnvelope;
import com.app_afesox.stsssox.events.sitemeta.SiteStatusDTO;
import com.app_afesox.stsssox.events.sitemeta.SiteTypeDTO;
import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.sitionix.wagssox.application.SiteMetaProjectionCommand;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.pipe.sitemeta.mapper.SiteMetaEventMapper;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SiteMetaConsumerTest {

    private CapturingSiteMetaProjectionCommand capturingSiteMetaProjectionCommand;
    private SiteMetaConsumer siteMetaConsumer;

    @BeforeEach
    void setUp() {
        this.capturingSiteMetaProjectionCommand = new CapturingSiteMetaProjectionCommand();
        this.siteMetaConsumer = new SiteMetaConsumer(this.capturingSiteMetaProjectionCommand, new SiteMetaEventMapper());
    }

    @Test
    void givenCreatedPayload_whenConsumeSiteMeta_thenApplySiteCreatedProjection() {
        //given
        final SiteCreatedEvent siteCreatedEvent = SiteCreatedEvent.newBuilder()
                .setSiteId("55db7314-63a5-49b5-bdb6-6a6cc59e61b9")
                .setOwnerUserId(17L)
                .setName("Site A")
                .setStatus(SiteStatusDTO.DRAFT)
                .setType(SiteTypeDTO.BLOG)
                .setDescription("Description")
                .setCreatedAt("2026-02-18T10:00:00Z")
                .setUpdatedAt("2026-02-18T10:00:00Z")
                .build();
        final SiteMetaEnvelope siteMetaEnvelope = this.getEnvelope(siteCreatedEvent);

        //when
        this.siteMetaConsumer.consumeSiteMeta(siteMetaEnvelope);

        //then
        assertThat(this.capturingSiteMetaProjectionCommand.createdSiteMeta).isNotNull();
        assertThat(this.capturingSiteMetaProjectionCommand.createdSiteMeta.siteId())
                .isEqualTo(UUID.fromString("55db7314-63a5-49b5-bdb6-6a6cc59e61b9"));
        assertThat(this.capturingSiteMetaProjectionCommand.createdSiteMeta.ownerUserId()).isEqualTo(17L);
    }

    @Test
    void givenUpdatedPayload_whenConsumeSiteMeta_thenApplySiteUpdatedProjection() {
        //given
        final SiteUpdatedEvent siteUpdatedEvent = SiteUpdatedEvent.newBuilder()
                .setSiteId("d66d5d41-a121-4347-a245-0082bf9c2038")
                .setOwnerUserId(19L)
                .setName("Site B")
                .setStatus(SiteStatusDTO.PUBLISHED)
                .setType(SiteTypeDTO.BUSINESS)
                .setDescription("Updated")
                .setUpdatedAt("2026-02-18T12:00:00Z")
                .build();
        final SiteMetaEnvelope siteMetaEnvelope = this.getEnvelope(siteUpdatedEvent);

        //when
        this.siteMetaConsumer.consumeSiteMeta(siteMetaEnvelope);

        //then
        assertThat(this.capturingSiteMetaProjectionCommand.updatedSiteId)
                .isEqualTo(UUID.fromString("d66d5d41-a121-4347-a245-0082bf9c2038"));
        assertThat(this.capturingSiteMetaProjectionCommand.updatedOwnerUserId).isEqualTo(19L);
        assertThat(this.capturingSiteMetaProjectionCommand.updatedName).isEqualTo("Site B");
        assertThat(this.capturingSiteMetaProjectionCommand.updatedStatus).isEqualTo(WorkspaceSiteMetaStatus.PUBLISHED);
        assertThat(this.capturingSiteMetaProjectionCommand.updatedType).isEqualTo(WorkspaceSiteMetaType.BUSINESS);
        assertThat(this.capturingSiteMetaProjectionCommand.updatedDescription).isEqualTo("Updated");
        assertThat(this.capturingSiteMetaProjectionCommand.updatedAt).isEqualTo(Instant.parse("2026-02-18T12:00:00Z"));
    }

    @Test
    void givenDeletedPayload_whenConsumeSiteMeta_thenApplySiteDeletedProjection() {
        //given
        final SiteDeletedEvent siteDeletedEvent = SiteDeletedEvent.newBuilder()
                .setSiteId("cf43e355-f67a-426d-9986-9f55fe3934ff")
                .setOwnerUserId(25L)
                .setDeletedAt("2026-02-18T12:10:00Z")
                .build();
        final SiteMetaEnvelope siteMetaEnvelope = this.getEnvelope(siteDeletedEvent);

        //when
        this.siteMetaConsumer.consumeSiteMeta(siteMetaEnvelope);

        //then
        assertThat(this.capturingSiteMetaProjectionCommand.deletedSiteId)
                .isEqualTo(UUID.fromString("cf43e355-f67a-426d-9986-9f55fe3934ff"));
    }

    private SiteMetaEnvelope getEnvelope(final Object payload) {
        return SiteMetaEnvelope.newBuilder()
                .setMetadata(
                        Metadata.newBuilder()
                                .setIdempotencyId(UUID.randomUUID().toString())
                                .setCreatedAt(Instant.now().toEpochMilli())
                                .setEventType("SITE_META_TEST")
                                .build()
                )
                .setPayload(payload)
                .build();
    }

    private static final class CapturingSiteMetaProjectionCommand implements SiteMetaProjectionCommand {

        private WorkspaceSiteMeta createdSiteMeta;
        private UUID updatedSiteId;
        private Long updatedOwnerUserId;
        private String updatedName;
        private WorkspaceSiteMetaStatus updatedStatus;
        private WorkspaceSiteMetaType updatedType;
        private String updatedDescription;
        private Instant updatedAt;
        private UUID deletedSiteId;

        @Override
        public void applySiteCreated(final WorkspaceSiteMeta siteMeta) {
            this.createdSiteMeta = siteMeta;
        }

        @Override
        public void applySiteUpdated(final UUID siteId,
                                     final Long ownerUserId,
                                     final String name,
                                     final WorkspaceSiteMetaStatus status,
                                     final WorkspaceSiteMetaType type,
                                     final String description,
                                     final Instant updatedAt) {
            this.updatedSiteId = siteId;
            this.updatedOwnerUserId = ownerUserId;
            this.updatedName = name;
            this.updatedStatus = status;
            this.updatedType = type;
            this.updatedDescription = description;
            this.updatedAt = updatedAt;
        }

        @Override
        public void applySiteDeleted(final UUID siteId) {
            this.deletedSiteId = siteId;
        }
    }
}
