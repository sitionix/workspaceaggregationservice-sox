package com.sitionix.wagssox.pipe.sitemeta.handler;

import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.sitionix.wagssox.application.SiteMetaProjectionCommand;
import com.sitionix.wagssox.pipe.sitemeta.mapper.SiteMetaEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SiteUpdatedPayloadHandler implements SiteMetaPayloadHandler<SiteUpdatedEvent> {

    private final SiteMetaProjectionCommand siteMetaProjectionCommand;
    private final SiteMetaEventMapper siteMetaEventMapper;

    @Override
    public Class<SiteUpdatedEvent> supports() {
        return SiteUpdatedEvent.class;
    }

    @Override
    public void handle(final SiteUpdatedEvent payload) {
        this.siteMetaProjectionCommand.applySiteUpdated(
                this.siteMetaEventMapper.asSiteId(payload),
                this.siteMetaEventMapper.asOwnerUserId(payload),
                this.siteMetaEventMapper.asName(payload),
                this.siteMetaEventMapper.asStatus(payload),
                this.siteMetaEventMapper.asType(payload),
                this.siteMetaEventMapper.asDescription(payload),
                this.siteMetaEventMapper.asUpdatedAt(payload)
        );
    }
}
