package com.sitionix.wagssox.domain.event;

import com.sitionix.wagssox.domain.event.payload.SiteCreatedInboxPayload;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SiteMetaEventTypeTest {

    @Test
    void givenKnownId_whenFromId_thenReturnEventType() {
        //given
        final Long id = 1L;

        //when
        final SiteMetaEventType actual = SiteMetaEventType.fromId(id);

        //then
        assertThat(actual).isEqualTo(SiteMetaEventType.SITE_CREATED);
    }

    @Test
    void givenUnknownId_whenFromId_thenThrowIllegalArgumentException() {
        //given
        final Long id = 99L;

        //when //then
        assertThatThrownBy(() -> SiteMetaEventType.fromId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No enum value found for id: 99");
    }

    @Test
    void givenKnownDescription_whenFromDescription_thenReturnEventType() {
        //given
        final String description = "SITE_UPDATED";

        //when
        final SiteMetaEventType actual = SiteMetaEventType.fromDescription(description);

        //then
        assertThat(actual).isEqualTo(SiteMetaEventType.SITE_UPDATED);
    }

    @Test
    void givenUnknownDescription_whenFromDescription_thenThrowIllegalArgumentException() {
        //given
        final String description = "UNKNOWN";

        //when //then
        assertThatThrownBy(() -> SiteMetaEventType.fromDescription(description))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No enum value found for description: UNKNOWN");
    }

    @Test
    void givenSiteCreatedEventType_whenPayloadClass_thenReturnPayloadClass() {
        //given
        final SiteMetaEventType eventType = SiteMetaEventType.SITE_CREATED;

        //when
        final Class<?> actual = eventType.payloadClass();

        //then
        assertThat(actual).isEqualTo(SiteCreatedInboxPayload.class);
    }
}

