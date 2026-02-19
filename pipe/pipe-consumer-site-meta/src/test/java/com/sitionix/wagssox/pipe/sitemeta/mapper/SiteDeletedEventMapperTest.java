package com.sitionix.wagssox.pipe.sitemeta.mapper;

import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.sitionix.wagssox.domain.SiteMetaDelete;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SiteDeletedEventMapperTest {

    private SiteDeletedEventMapper siteDeletedEventMapper;

    @BeforeEach
    void setUp() {
        this.siteDeletedEventMapper = new SiteDeletedEventMapperImpl();
    }

    @Test
    void givenSiteDeletedEvent_whenAsProjection_thenReturnProjection() {
        //given
        final SiteDeletedEvent payload = new SiteDeletedEvent(
                "55db7314-63a5-49b5-bdb6-6a6cc59e61b9",
                17L,
                "2026-02-18T10:00:00Z"
        );
        final SiteMetaDelete expected = SiteMetaDelete.builder()
                .siteId(UUID.fromString("55db7314-63a5-49b5-bdb6-6a6cc59e61b9"))
                .userId(17L)
                .deletedAt(Instant.parse("2026-02-18T10:00:00Z"))
                .build();

        //when
        final SiteMetaDelete actual = this.siteDeletedEventMapper.asProjection(payload);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenSiteDeletedEventWithNullOptionalFields_whenAsProjection_thenReturnProjectionWithNulls() {
        //given
        final SiteDeletedEvent payload = new SiteDeletedEvent(null, 17L, null);
        final SiteMetaDelete expected = SiteMetaDelete.builder()
                .siteId(null)
                .userId(17L)
                .deletedAt(null)
                .build();

        //when
        final SiteMetaDelete actual = this.siteDeletedEventMapper.asProjection(payload);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
