package com.sitionix.wagssox.application.inbox;

import com.sitionix.forge.inbox.core.model.InboxEvent;
import com.sitionix.forge.inbox.core.port.ForgeInboxEventHandler;
import com.sitionix.wagssox.domain.event.payload.SiteDeletedInboxPayload;
import com.sitionix.wagssox.domain.usecase.SiteMetaProjectionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SiteDeletedInboxEventHandler implements ForgeInboxEventHandler<SiteDeletedInboxPayload> {

    private final SiteMetaProjectionCommand siteMetaProjectionCommand;

    @Override
    public void handle(final InboxEvent<SiteDeletedInboxPayload> event) {
        this.siteMetaProjectionCommand.applySiteDeleted(event.getPayload().siteMetaDelete());
    }
}
