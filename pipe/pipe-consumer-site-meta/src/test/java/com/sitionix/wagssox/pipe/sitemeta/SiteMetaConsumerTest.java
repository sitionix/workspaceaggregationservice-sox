package com.sitionix.wagssox.pipe.sitemeta;

import com.app_afesox.events.Metadata;
import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteMetaEnvelope;
import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.sitionix.forge.inbox.core.port.ForgeInbox;
import com.sitionix.forge.inbox.core.port.ForgeInboxPayload;
import com.sitionix.wagssox.domain.SiteMetaDelete;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.event.payload.SiteCreatedInboxPayload;
import com.sitionix.wagssox.domain.event.payload.SiteDeletedInboxPayload;
import com.sitionix.wagssox.domain.event.payload.SiteUpdatedInboxPayload;
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
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.ArgumentCaptor;

@ExtendWith(MockitoExtension.class)
class SiteMetaConsumerTest {

    @Mock
    private ForgeInbox<ForgeInboxPayload> forgeInbox;

    @Mock
    private SiteMetaEventMapper siteMetaEventMapper;

    private SiteMetaConsumer siteMetaConsumer;

    @BeforeEach
    void setUp() {
        this.siteMetaConsumer = new SiteMetaConsumer(this.forgeInbox, this.siteMetaEventMapper);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.forgeInbox, this.siteMetaEventMapper);
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
    void givenCreatedPayload_whenConsumeSiteMeta_thenCreateInboxRecord() {
        //given
        final SiteMetaEnvelope envelope = mock(SiteMetaEnvelope.class);
        final Metadata metadata = mock(Metadata.class);
        final SiteCreatedEvent payload = mock(SiteCreatedEvent.class);
        final WorkspaceSiteMeta siteMeta = mock(WorkspaceSiteMeta.class);
        when(envelope.getPayload()).thenReturn(payload);
        when(envelope.getMetadata()).thenReturn(metadata);
        when(metadata.getIdempotencyId()).thenReturn("idemp-1");
        when(this.siteMetaEventMapper.asProjection(payload, WorkspaceSiteMeta.class)).thenReturn(siteMeta);
        final ArgumentCaptor<ForgeInboxPayload> payloadCaptor = ArgumentCaptor.forClass(ForgeInboxPayload.class);

        //when
        this.siteMetaConsumer.consumeSiteMeta(envelope);

        //then
        verify(envelope, times(2)).getPayload();
        verify(envelope).getMetadata();
        verify(metadata).getIdempotencyId();
        verify(this.siteMetaEventMapper).asProjection(payload, WorkspaceSiteMeta.class);
        verify(this.forgeInbox).receive(payloadCaptor.capture());
        assertThat(payloadCaptor.getValue()).isEqualTo(new SiteCreatedInboxPayload(siteMeta, "idemp-1"));
        verifyNoMoreInteractions(envelope, payload, metadata);
    }

    @Test
    void givenUpdatedPayload_whenConsumeSiteMeta_thenCreateInboxRecord() {
        //given
        final SiteMetaEnvelope envelope = mock(SiteMetaEnvelope.class);
        final Metadata metadata = mock(Metadata.class);
        final SiteUpdatedEvent payload = mock(SiteUpdatedEvent.class);
        final SiteMetaUpdate siteMetaUpdate = mock(SiteMetaUpdate.class);
        when(envelope.getPayload()).thenReturn(payload);
        when(envelope.getMetadata()).thenReturn(metadata);
        when(metadata.getIdempotencyId()).thenReturn("idemp-2");
        when(this.siteMetaEventMapper.asProjection(payload, SiteMetaUpdate.class)).thenReturn(siteMetaUpdate);
        final ArgumentCaptor<ForgeInboxPayload> payloadCaptor = ArgumentCaptor.forClass(ForgeInboxPayload.class);

        //when
        this.siteMetaConsumer.consumeSiteMeta(envelope);

        //then
        verify(envelope, times(2)).getPayload();
        verify(envelope).getMetadata();
        verify(metadata).getIdempotencyId();
        verify(this.siteMetaEventMapper).asProjection(payload, SiteMetaUpdate.class);
        verify(this.forgeInbox).receive(payloadCaptor.capture());
        assertThat(payloadCaptor.getValue()).isEqualTo(new SiteUpdatedInboxPayload(siteMetaUpdate, "idemp-2"));
        verifyNoMoreInteractions(envelope, payload, metadata);
    }

    @Test
    void givenDeletedPayload_whenConsumeSiteMeta_thenCreateInboxRecord() {
        //given
        final SiteMetaEnvelope envelope = mock(SiteMetaEnvelope.class);
        final Metadata metadata = mock(Metadata.class);
        final SiteDeletedEvent payload = mock(SiteDeletedEvent.class);
        final SiteMetaDelete siteMetaDelete = mock(SiteMetaDelete.class);
        when(envelope.getPayload()).thenReturn(payload);
        when(envelope.getMetadata()).thenReturn(metadata);
        when(metadata.getIdempotencyId()).thenReturn("idemp-3");
        when(this.siteMetaEventMapper.asProjection(payload, SiteMetaDelete.class)).thenReturn(siteMetaDelete);
        final ArgumentCaptor<ForgeInboxPayload> payloadCaptor = ArgumentCaptor.forClass(ForgeInboxPayload.class);

        //when
        this.siteMetaConsumer.consumeSiteMeta(envelope);

        //then
        verify(envelope, times(2)).getPayload();
        verify(envelope).getMetadata();
        verify(metadata).getIdempotencyId();
        verify(this.siteMetaEventMapper).asProjection(payload, SiteMetaDelete.class);
        verify(this.forgeInbox).receive(payloadCaptor.capture());
        assertThat(payloadCaptor.getValue()).isEqualTo(new SiteDeletedInboxPayload(siteMetaDelete, "idemp-3"));
        verifyNoMoreInteractions(envelope, payload, metadata);
    }

    @Test
    void givenPayloadWithNullMetadata_whenConsumeSiteMeta_thenCreateInboxRecordWithNullIdempotencyKey() {
        //given
        final SiteMetaEnvelope envelope = mock(SiteMetaEnvelope.class);
        final SiteCreatedEvent payload = mock(SiteCreatedEvent.class);
        final WorkspaceSiteMeta siteMeta = mock(WorkspaceSiteMeta.class);
        when(envelope.getPayload()).thenReturn(payload);
        when(envelope.getMetadata()).thenReturn(null);
        when(this.siteMetaEventMapper.asProjection(payload, WorkspaceSiteMeta.class)).thenReturn(siteMeta);
        final ArgumentCaptor<ForgeInboxPayload> payloadCaptor = ArgumentCaptor.forClass(ForgeInboxPayload.class);

        //when
        this.siteMetaConsumer.consumeSiteMeta(envelope);

        //then
        verify(envelope, times(2)).getPayload();
        verify(envelope).getMetadata();
        verify(this.siteMetaEventMapper).asProjection(payload, WorkspaceSiteMeta.class);
        verify(this.forgeInbox).receive(payloadCaptor.capture());
        assertThat(payloadCaptor.getValue()).isEqualTo(new SiteCreatedInboxPayload(siteMeta, null));
        verifyNoMoreInteractions(envelope, payload);
    }

    @Test
    void givenUnsupportedPayload_whenConsumeSiteMeta_thenSkipInboxRecordCreation() {
        //given
        final SiteMetaEnvelope envelope = mock(SiteMetaEnvelope.class);
        final Metadata metadata = mock(Metadata.class);
        final Object payload = mock(Object.class);
        when(envelope.getPayload()).thenReturn(payload);
        when(envelope.getMetadata()).thenReturn(metadata);
        when(metadata.getIdempotencyId()).thenReturn("idemp-unsupported");

        //when
        this.siteMetaConsumer.consumeSiteMeta(envelope);

        //then
        verify(envelope, times(2)).getPayload();
        verify(envelope).getMetadata();
        verify(metadata).getIdempotencyId();
        verifyNoMoreInteractions(envelope, payload, metadata);
    }
}
