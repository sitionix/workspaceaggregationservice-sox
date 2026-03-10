package com.sitionix.wagssox.application.inbox;

import com.sitionix.forge.inbox.core.model.InboxEvent;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.event.payload.SiteUpdatedInboxPayload;
import com.sitionix.wagssox.domain.usecase.SiteMetaProjectionCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class SiteUpdatedInboxEventHandlerTest {

    @Mock
    private SiteMetaProjectionCommand siteMetaProjectionCommand;

    private SiteUpdatedInboxEventHandler siteUpdatedInboxEventHandler;

    @BeforeEach
    void setUp() {
        this.siteUpdatedInboxEventHandler = new SiteUpdatedInboxEventHandler(this.siteMetaProjectionCommand);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.siteMetaProjectionCommand);
    }

    @Test
    void givenSiteUpdatedPayload_whenHandle_thenApplySiteUpdatedProjection() {
        //given
        final SiteMetaUpdate siteMetaUpdate = mock(SiteMetaUpdate.class);
        final SiteUpdatedInboxPayload payload = new SiteUpdatedInboxPayload(siteMetaUpdate, "idempotency-2");
        final InboxEvent<SiteUpdatedInboxPayload> event = InboxEvent.<SiteUpdatedInboxPayload>builder()
                .eventType("SITE_UPDATED")
                .payload(payload)
                .build();

        //when
        this.siteUpdatedInboxEventHandler.handle(event);

        //then
        verify(this.siteMetaProjectionCommand).applySiteUpdated(siteMetaUpdate);
    }
}
