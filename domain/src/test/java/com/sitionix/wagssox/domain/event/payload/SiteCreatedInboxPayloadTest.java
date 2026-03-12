package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SiteCreatedInboxPayloadTest {

    @Test
    void givenSiteMeta_whenCreatePayload_thenReturnSameSiteMeta() {
        //given
        final WorkspaceSiteMeta siteMeta = mock(WorkspaceSiteMeta.class);

        //when
        final SiteCreatedInboxPayload actual = new SiteCreatedInboxPayload(siteMeta);

        //then
        assertThat(actual.siteMeta()).isEqualTo(siteMeta);
    }
}

