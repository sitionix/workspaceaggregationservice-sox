package com.sitionix.wagssox.pipe.sitemeta;

import com.app_afesox.events.Metadata;
import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteMetaEnvelope;
import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.app_afesox.stsssox.events.sitemeta.kafka.SitemetaV1ConsumerHandler;
import com.sitionix.forge.inbox.core.port.ForgeInbox;
import com.sitionix.forge.inbox.core.port.ForgeInboxPayload;
import com.sitionix.wagssox.domain.SiteMetaDelete;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
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
        if (isNull(siteMetaEnvelope) || isNull(siteMetaEnvelope.getPayload())) {
            return;
        }
        final Metadata metadata = siteMetaEnvelope.getMetadata();
        final SiteMetaInboxPayload inboxPayload = this.asInboxPayload(
                siteMetaEnvelope.getPayload(),
                metadata == null ? null : metadata.getIdempotencyId()
        );
        if (inboxPayload == null) {
            return;
        }
        this.forgeInbox.receive(inboxPayload);
    }

    private SiteMetaInboxPayload asInboxPayload(final Object payload,
                                                final String idempotencyKey) {
        switch (payload) {
            case SiteCreatedEvent createdEvent -> {
                final WorkspaceSiteMeta siteMeta = this.siteMetaEventMapper.asProjection(createdEvent, WorkspaceSiteMeta.class);
                return new SiteCreatedInboxPayload(siteMeta, idempotencyKey);
            }
            case SiteUpdatedEvent updatedEvent -> {
                final SiteMetaUpdate siteMetaUpdate = this.siteMetaEventMapper.asProjection(updatedEvent, SiteMetaUpdate.class);
                return new SiteUpdatedInboxPayload(siteMetaUpdate, idempotencyKey);
            }
            case SiteDeletedEvent deletedEvent -> {
                final SiteMetaDelete siteMetaDelete = this.siteMetaEventMapper.asProjection(deletedEvent, SiteMetaDelete.class);
                return new SiteDeletedInboxPayload(siteMetaDelete, idempotencyKey);
            }
            default -> {
                log.warn("Skip unsupported site-meta payload type: {}", payload.getClass().getName());
                return null;
            }
        }
    }
}
