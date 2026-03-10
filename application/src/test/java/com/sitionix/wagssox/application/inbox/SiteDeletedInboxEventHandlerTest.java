package com.sitionix.wagssox.application.inbox;

import com.sitionix.forge.inbox.core.model.InboxEvent;
import com.sitionix.wagssox.domain.SiteMetaDelete;
import com.sitionix.wagssox.domain.event.payload.SiteDeletedInboxPayload;
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
class SiteDeletedInboxEventHandlerTest {

    @Mock
    private SiteMetaProjectionCommand siteMetaProjectionCommand;

    private SiteDeletedInboxEventHandler siteDeletedInboxEventHandler;

    @BeforeEach
    void setUp() {
        this.siteDeletedInboxEventHandler = new SiteDeletedInboxEventHandler(this.siteMetaProjectionCommand);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.siteMetaProjectionCommand);
    }

    @Test
    void givenSiteDeletedPayload_whenHandle_thenApplySiteDeletedProjection() {
        //given
        final SiteMetaDelete siteMetaDelete = mock(SiteMetaDelete.class);
        final SiteDeletedInboxPayload payload = new SiteDeletedInboxPayload(siteMetaDelete, "idempotency-3");
        final InboxEvent<SiteDeletedInboxPayload> event = InboxEvent.<SiteDeletedInboxPayload>builder()
                .eventType("SITE_DELETED")
                .payload(payload)
                .build();

        //when
        this.siteDeletedInboxEventHandler.handle(event);

        //then
        verify(this.siteMetaProjectionCommand).applySiteDeleted(siteMetaDelete);
    }
}
