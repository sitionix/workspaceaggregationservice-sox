package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.wagssox.domain.SiteMetaDelete;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SiteDeletedInboxPayloadTest {

    @Test
    void givenSiteMetaDelete_whenCreatePayload_thenReturnSameSiteMetaDelete() {
        //given
        final SiteMetaDelete siteMetaDelete = mock(SiteMetaDelete.class);

        //when
        final SiteDeletedInboxPayload actual = new SiteDeletedInboxPayload(siteMetaDelete);

        //then
        assertThat(actual.siteMetaDelete()).isEqualTo(siteMetaDelete);
    }
}

