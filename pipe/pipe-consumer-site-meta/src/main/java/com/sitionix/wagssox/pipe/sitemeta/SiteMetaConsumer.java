package com.sitionix.wagssox.pipe.sitemeta;

import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteMetaEnvelope;
import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.app_afesox.stsssox.events.sitemeta.kafka.SitemetaV1ConsumerHandler;
import com.sitionix.wagssox.application.SiteMetaProjectionCommand;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.pipe.sitemeta.mapper.SiteMetaEventMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiteMetaConsumer implements SitemetaV1ConsumerHandler {

    private final SiteMetaProjectionCommand siteMetaProjectionCommand;
    private final SiteMetaEventMapper siteMetaEventMapper;

    @Override
    public void consumeSiteMeta(final SiteMetaEnvelope siteMetaEnvelope) {
        if (isNull(siteMetaEnvelope) || isNull(siteMetaEnvelope.getPayload())) {
            return;
        }
        this.handlePayload(siteMetaEnvelope.getPayload());
    }

    private void handlePayload(final Object payload) {
        switch (payload) {
            case SiteCreatedEvent createdEvent ->
                    this.siteMetaProjectionCommand.applySiteCreated(this.siteMetaEventMapper.asProjection(createdEvent, WorkspaceSiteMeta.class));
            case SiteUpdatedEvent updatedEvent ->
                    this.siteMetaProjectionCommand.applySiteUpdated(this.siteMetaEventMapper.asProjection(updatedEvent, SiteMetaUpdate.class));
            case SiteDeletedEvent deletedEvent ->
                    this.siteMetaProjectionCommand.applySiteDeleted(this.siteMetaEventMapper.asProjection(deletedEvent, UUID.class));
            default -> log.warn("Skip unsupported site-meta payload type: {}", payload.getClass().getName());
        }
    }
}
