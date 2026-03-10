package com.sitionix.wagssox.application.inbox;

import com.sitionix.forge.inbox.core.model.InboxEvent;
import com.sitionix.forge.inbox.core.port.ForgeInboxEventHandler;
import com.sitionix.wagssox.domain.event.payload.SiteCreatedInboxPayload;
import com.sitionix.wagssox.domain.usecase.SiteMetaProjectionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SiteCreatedInboxEventHandler implements ForgeInboxEventHandler<SiteCreatedInboxPayload> {

    private final SiteMetaProjectionCommand siteMetaProjectionCommand;

    @Override
    public Class<SiteCreatedInboxPayload> payloadClass() {
        return SiteCreatedInboxPayload.class;
    }

    @Override
    public void handle(final InboxEvent<SiteCreatedInboxPayload> event) {
        this.siteMetaProjectionCommand.applySiteCreated(event.getPayload().siteMeta());
    }
}
