package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.wagssox.domain.SiteMetaUpdate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SiteUpdatedInboxPayloadTest {

    @Test
    void givenSiteMetaUpdate_whenCreatePayload_thenReturnSameSiteMetaUpdate() {
        //given
        final SiteMetaUpdate siteMetaUpdate = mock(SiteMetaUpdate.class);

        //when
        final SiteUpdatedInboxPayload actual = new SiteUpdatedInboxPayload(siteMetaUpdate);

        //then
        assertThat(actual.siteMetaUpdate()).isEqualTo(siteMetaUpdate);
    }
}

