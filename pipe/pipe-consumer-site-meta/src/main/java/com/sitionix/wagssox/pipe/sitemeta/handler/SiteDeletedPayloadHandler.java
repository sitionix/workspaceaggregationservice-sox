package com.sitionix.wagssox.pipe.sitemeta.handler;

import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.sitionix.wagssox.application.SiteMetaProjectionCommand;
import com.sitionix.wagssox.pipe.sitemeta.mapper.SiteMetaEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SiteDeletedPayloadHandler implements SiteMetaPayloadHandler<SiteDeletedEvent> {

    private final SiteMetaProjectionCommand siteMetaProjectionCommand;
    private final SiteMetaEventMapper siteMetaEventMapper;

    @Override
    public Class<SiteDeletedEvent> supports() {
        return SiteDeletedEvent.class;
    }

    @Override
    public void handle(final SiteDeletedEvent payload) {
        this.siteMetaProjectionCommand.applySiteDeleted(this.siteMetaEventMapper.asSiteId(payload));
    }
}
