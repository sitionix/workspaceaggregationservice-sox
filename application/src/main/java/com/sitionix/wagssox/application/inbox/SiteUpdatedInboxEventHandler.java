package com.sitionix.wagssox.application.inbox;

import com.sitionix.forge.inbox.core.model.InboxEvent;
import com.sitionix.forge.inbox.core.port.ForgeInboxEventHandler;
import com.sitionix.wagssox.domain.event.payload.SiteUpdatedInboxPayload;
import com.sitionix.wagssox.domain.usecase.SiteMetaProjectionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SiteUpdatedInboxEventHandler implements ForgeInboxEventHandler<SiteUpdatedInboxPayload> {

    private final SiteMetaProjectionCommand siteMetaProjectionCommand;

    @Override
    public void handle(final InboxEvent<SiteUpdatedInboxPayload> event) {
        this.siteMetaProjectionCommand.applySiteUpdated(event.getPayload().siteMetaUpdate());
    }
}
