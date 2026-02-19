package com.sitionix.wagssox.pipe.sitemeta.handler;

import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.sitionix.wagssox.application.SiteMetaProjectionCommand;
import com.sitionix.wagssox.pipe.sitemeta.mapper.SiteMetaEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SiteCreatedPayloadHandler implements SiteMetaPayloadHandler<SiteCreatedEvent> {

    private final SiteMetaProjectionCommand siteMetaProjectionCommand;
    private final SiteMetaEventMapper siteMetaEventMapper;

    @Override
    public Class<SiteCreatedEvent> supports() {
        return SiteCreatedEvent.class;
    }

    @Override
    public void handle(final SiteCreatedEvent payload) {
        this.siteMetaProjectionCommand.applySiteCreated(this.siteMetaEventMapper.asSiteMeta(payload));
    }
}
