package com.sitionix.wagssox.pipe.sitemeta;

import com.app_afesox.events.Metadata;
import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteMetaEnvelope;
import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.app_afesox.stsssox.events.sitemeta.kafka.SitemetaV1ConsumerHandler;
import com.sitionix.forge.inbox.core.port.ForgeInbox;
import com.sitionix.forge.inbox.core.port.ForgeInboxPayload;
import com.sitionix.forge.inbox.core.port.InboxReceiveMetadata;
import com.sitionix.wagssox.domain.SiteMetaDelete;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.event.SiteMetaEventType;
import com.sitionix.wagssox.domain.event.payload.SiteCreatedInboxPayload;
import com.sitionix.wagssox.domain.event.payload.SiteDeletedInboxPayload;
import com.sitionix.wagssox.domain.event.payload.SiteMetaInboxPayload;
import com.sitionix.wagssox.domain.event.payload.SiteUpdatedInboxPayload;
import com.sitionix.wagssox.pipe.sitemeta.mapper.SiteMetaEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiteMetaConsumer implements SitemetaV1ConsumerHandler {

    private final ForgeInbox<ForgeInboxPayload> forgeInbox;
    private final SiteMetaEventMapper siteMetaEventMapper;

    @Override
    public void consumeSiteMeta(final SiteMetaEnvelope siteMetaEnvelope) {
        if (isNull(siteMetaEnvelope)) {
            return;
        }
        final Object payload = siteMetaEnvelope.getPayload();
        if (isNull(payload)) {
            return;
        }
        final Metadata metadata = siteMetaEnvelope.getMetadata();
        final String eventType = this.resolveEventType(metadata);
        if (isNull(eventType)) {
            return;
        }
        final String idempotencyKey = metadata.getIdempotencyId();
        final SiteMetaInboxPayload inboxPayload = this.asInboxPayload(payload);
        if (inboxPayload == null) {
            return;
        }
        this.forgeInbox.receive(inboxPayload, new InboxReceiveMetadata(eventType, idempotencyKey, null));
    }

    private SiteMetaInboxPayload asInboxPayload(final Object payload) {
        switch (payload) {
            case SiteCreatedEvent createdEvent -> {
                final WorkspaceSiteMeta siteMeta = this.siteMetaEventMapper.asProjection(createdEvent, WorkspaceSiteMeta.class);
                return new SiteCreatedInboxPayload(siteMeta);
            }
            case SiteUpdatedEvent updatedEvent -> {
                final SiteMetaUpdate siteMetaUpdate = this.siteMetaEventMapper.asProjection(updatedEvent, SiteMetaUpdate.class);
                return new SiteUpdatedInboxPayload(siteMetaUpdate);
            }
            case SiteDeletedEvent deletedEvent -> {
                final SiteMetaDelete siteMetaDelete = this.siteMetaEventMapper.asProjection(deletedEvent, SiteMetaDelete.class);
                return new SiteDeletedInboxPayload(siteMetaDelete);
            }
            default -> {
                log.warn("Skip unsupported site-meta payload type: {}", payload.getClass().getName());
                return null;
            }
        }
    }

    private String resolveEventType(final Metadata metadata) {
        if (metadata == null) {
            log.warn("Skip site-meta message because metadata.eventType is missing");
            return null;
        }
        final String metadataEventType = metadata.getEventType();
        if (metadataEventType == null || metadataEventType.isBlank()) {
            log.warn("Skip site-meta message because metadata.eventType is missing");
            return null;
        }

        final String eventType = metadataEventType.trim();
        try {
            SiteMetaEventType.fromDescription(eventType);
            return eventType;
        } catch (final IllegalArgumentException exception) {
            log.warn("Skip site-meta message because metadata.eventType is unsupported: {}", metadataEventType);
            return null;
        }
    }
}
