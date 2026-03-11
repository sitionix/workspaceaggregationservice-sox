package com.sitionix.wagssox.application.inbox;

import com.sitionix.forge.inbox.core.model.InboxEvent;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.event.payload.SiteCreatedInboxPayload;
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
class SiteCreatedInboxEventHandlerTest {

    @Mock
    private SiteMetaProjectionCommand siteMetaProjectionCommand;

    private SiteCreatedInboxEventHandler siteCreatedInboxEventHandler;

    @BeforeEach
    void setUp() {
        this.siteCreatedInboxEventHandler = new SiteCreatedInboxEventHandler(this.siteMetaProjectionCommand);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.siteMetaProjectionCommand);
    }

    @Test
    void givenSiteCreatedPayload_whenHandle_thenApplySiteCreatedProjection() {
        //given
        final WorkspaceSiteMeta siteMeta = mock(WorkspaceSiteMeta.class);
        final SiteCreatedInboxPayload payload = new SiteCreatedInboxPayload(siteMeta);
        final InboxEvent<SiteCreatedInboxPayload> event = InboxEvent.<SiteCreatedInboxPayload>builder()
                .eventType("SITE_CREATED")
                .payload(payload)
                .build();

        //when
        this.siteCreatedInboxEventHandler.handle(event);

        //then
        verify(this.siteMetaProjectionCommand).applySiteCreated(siteMeta);
    }
}
