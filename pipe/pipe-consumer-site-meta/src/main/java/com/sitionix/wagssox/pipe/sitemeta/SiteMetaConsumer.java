package com.sitionix.wagssox.pipe.sitemeta;

import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteMetaEnvelope;
import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.app_afesox.stsssox.events.sitemeta.kafka.SitemetaV1ConsumerHandler;
import com.sitionix.wagssox.application.SiteMetaProjectionCommand;
import com.sitionix.wagssox.pipe.sitemeta.mapper.SiteMetaEventMapper;
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

        final Object payload = siteMetaEnvelope.getPayload();
        if (payload instanceof SiteCreatedEvent createdEvent) {
            this.siteMetaProjectionCommand.applySiteCreated(this.siteMetaEventMapper.asSiteMeta(createdEvent));
            return;
        }
        if (payload instanceof SiteUpdatedEvent updatedEvent) {
            this.siteMetaProjectionCommand.applySiteUpdated(
                    this.siteMetaEventMapper.asSiteId(updatedEvent),
                    this.siteMetaEventMapper.asOwnerUserId(updatedEvent),
                    this.siteMetaEventMapper.asName(updatedEvent),
                    this.siteMetaEventMapper.asStatus(updatedEvent),
                    this.siteMetaEventMapper.asType(updatedEvent),
                    this.siteMetaEventMapper.asDescription(updatedEvent),
                    this.siteMetaEventMapper.asUpdatedAt(updatedEvent)
            );
            return;
        }
        if (payload instanceof SiteDeletedEvent deletedEvent) {
            this.siteMetaProjectionCommand.applySiteDeleted(this.siteMetaEventMapper.asSiteId(deletedEvent));
            return;
        }
        log.warn("Skip unsupported site-meta payload type: {}", payload.getClass().getName());
    }
}
