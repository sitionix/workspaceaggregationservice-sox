package com.sitionix.wagssox.pipe.sitemeta;

import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteMetaEnvelope;
import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.sitionix.wagssox.application.SiteMetaProjectionCommand;
import com.sitionix.wagssox.domain.SiteMetaDelete;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.pipe.sitemeta.mapper.SiteMetaEventMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SiteMetaConsumerTest {

    @Mock
    private SiteMetaProjectionCommand siteMetaProjectionCommand;

    @Mock
    private SiteMetaEventMapper siteMetaEventMapper;

    private SiteMetaConsumer siteMetaConsumer;

    @BeforeEach
    void setUp() {
        this.siteMetaConsumer = new SiteMetaConsumer(this.siteMetaProjectionCommand, this.siteMetaEventMapper);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.siteMetaProjectionCommand, this.siteMetaEventMapper);
    }

    @Test
    void givenNullEnvelope_whenConsumeSiteMeta_thenSkipAllInteractions() {
        //when
        this.siteMetaConsumer.consumeSiteMeta(null);

        //then
    }

    @Test
    void givenEnvelopeWithNullPayload_whenConsumeSiteMeta_thenSkipAllInteractions() {
        //given
        final SiteMetaEnvelope envelope = mock(SiteMetaEnvelope.class);
        when(envelope.getPayload()).thenReturn(null);

        //when
        this.siteMetaConsumer.consumeSiteMeta(envelope);

        //then
        verify(envelope).getPayload();
        verifyNoMoreInteractions(envelope);
    }

    @Test
    void givenCreatedPayload_whenConsumeSiteMeta_thenApplySiteCreatedProjection() {
        //given
        final SiteMetaEnvelope envelope = mock(SiteMetaEnvelope.class);
        final SiteCreatedEvent payload = mock(SiteCreatedEvent.class);
        when(envelope.getPayload()).thenReturn(payload);
        when(this.siteMetaEventMapper.asProjection(payload, WorkspaceSiteMeta.class)).thenReturn(null);

        //when
        this.siteMetaConsumer.consumeSiteMeta(envelope);

        //then
        verify(envelope, times(2)).getPayload();
        verify(this.siteMetaEventMapper).asProjection(payload, WorkspaceSiteMeta.class);
        verify(this.siteMetaProjectionCommand).applySiteCreated(null);
        verifyNoMoreInteractions(envelope, payload);
    }

    @Test
    void givenUpdatedPayload_whenConsumeSiteMeta_thenApplySiteUpdatedProjection() {
        //given
        final SiteMetaEnvelope envelope = mock(SiteMetaEnvelope.class);
        final SiteUpdatedEvent payload = mock(SiteUpdatedEvent.class);
        when(envelope.getPayload()).thenReturn(payload);
        when(this.siteMetaEventMapper.asProjection(payload, SiteMetaUpdate.class)).thenReturn(null);

        //when
        this.siteMetaConsumer.consumeSiteMeta(envelope);

        //then
        verify(envelope, times(2)).getPayload();
        verify(this.siteMetaEventMapper).asProjection(payload, SiteMetaUpdate.class);
        verify(this.siteMetaProjectionCommand).applySiteUpdated(null);
        verifyNoMoreInteractions(envelope, payload);
    }

    @Test
    void givenDeletedPayload_whenConsumeSiteMeta_thenApplySiteDeletedProjection() {
        //given
        final SiteMetaEnvelope envelope = mock(SiteMetaEnvelope.class);
        final SiteDeletedEvent payload = mock(SiteDeletedEvent.class);
        final SiteMetaDelete siteMetaDelete = mock(SiteMetaDelete.class);
        when(envelope.getPayload()).thenReturn(payload);
        when(this.siteMetaEventMapper.asProjection(payload, SiteMetaDelete.class)).thenReturn(siteMetaDelete);

        //when
        this.siteMetaConsumer.consumeSiteMeta(envelope);

        //then
        verify(envelope, times(2)).getPayload();
        verify(this.siteMetaEventMapper).asProjection(payload, SiteMetaDelete.class);
        verify(this.siteMetaProjectionCommand).applySiteDeleted(siteMetaDelete);
        verifyNoMoreInteractions(envelope, payload);
    }

    @Test
    void givenUnsupportedPayload_whenConsumeSiteMeta_thenSkipProjectionCommand() {
        //given
        final SiteMetaEnvelope envelope = mock(SiteMetaEnvelope.class);
        final Object payload = mock(Object.class);
        when(envelope.getPayload()).thenReturn(payload);

        //when
        this.siteMetaConsumer.consumeSiteMeta(envelope);

        //then
        verify(envelope, times(2)).getPayload();
        verifyNoMoreInteractions(envelope, payload);
    }
}
