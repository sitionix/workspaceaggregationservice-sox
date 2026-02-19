package com.sitionix.wagssox.pipe.sitemeta.mapper;

import com.app_afesox.stsssox.events.sitemeta.SiteStatusDTO;
import com.app_afesox.stsssox.events.sitemeta.SiteTypeDTO;
import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class SiteUpdatedEventMapperTest {

    private SiteUpdatedEventMapper siteUpdatedEventMapper;

    @BeforeEach
    void setUp() {
        this.siteUpdatedEventMapper = new SiteUpdatedEventMapperImpl();
    }

    @Test
    void givenNullPayload_whenAsProjection_thenReturnNull() {
        //given
        final SiteUpdatedEvent payload = null;

        //when
        final SiteMetaUpdate actual = this.siteUpdatedEventMapper.asProjection(payload);

        //then
        assertThat(actual).isNull();
    }

    @ParameterizedTest
    @MethodSource("siteUpdatedEnums")
    void givenSiteUpdatedEventWithStatusAndType_whenAsProjection_thenReturnProjection(
            final SiteStatusDTO status,
            final SiteTypeDTO type,
            final WorkspaceSiteMetaStatus expectedStatus,
            final WorkspaceSiteMetaType expectedType
    ) {
        //given
        final SiteUpdatedEvent payload = this.getSiteUpdatedEvent(
                "55db7314-63a5-49b5-bdb6-6a6cc59e61b9",
                status,
                type,
                "Site",
                "Description",
                "2026-02-18T10:00:00Z"
        );
        final SiteMetaUpdate expected = this.getSiteMetaUpdate(expectedStatus, expectedType);

        //when
        final SiteMetaUpdate actual = this.siteUpdatedEventMapper.asProjection(payload);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenSiteUpdatedEventWithNullOptionalFields_whenAsProjection_thenReturnProjectionWithNulls() {
        //given
        final SiteUpdatedEvent payload = this.getSiteUpdatedEvent(
                "55db7314-63a5-49b5-bdb6-6a6cc59e61b9",
                null,
                null,
                null,
                null,
                "2026-02-18T10:00:00Z"
        );
        final SiteMetaUpdate expected = SiteMetaUpdate.builder()
                .siteId(UUID.fromString("55db7314-63a5-49b5-bdb6-6a6cc59e61b9"))
                .userId(17L)
                .name(null)
                .status(null)
                .type(null)
                .description(null)
                .updatedAt(Instant.parse("2026-02-18T10:00:00Z"))
                .build();

        //when
        final SiteMetaUpdate actual = this.siteUpdatedEventMapper.asProjection(payload);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> siteUpdatedEnums() {
        return Stream.of(
                Arguments.of(
                        SiteStatusDTO.DRAFT,
                        SiteTypeDTO.PORTFOLIO,
                        WorkspaceSiteMetaStatus.DRAFT,
                        WorkspaceSiteMetaType.PORTFOLIO
                ),
                Arguments.of(
                        SiteStatusDTO.PUBLISHED,
                        SiteTypeDTO.BUSINESS,
                        WorkspaceSiteMetaStatus.PUBLISHED,
                        WorkspaceSiteMetaType.BUSINESS
                ),
                Arguments.of(
                        SiteStatusDTO.ARCHIVED,
                        SiteTypeDTO.BLOG,
                        WorkspaceSiteMetaStatus.ARCHIVED,
                        WorkspaceSiteMetaType.BLOG
                ),
                Arguments.of(
                        SiteStatusDTO.DRAFT,
                        SiteTypeDTO.STORE,
                        WorkspaceSiteMetaStatus.DRAFT,
                        WorkspaceSiteMetaType.STORE
                ),
                Arguments.of(
                        SiteStatusDTO.PUBLISHED,
                        SiteTypeDTO.LANDING,
                        WorkspaceSiteMetaStatus.PUBLISHED,
                        WorkspaceSiteMetaType.LANDING
                ),
                Arguments.of(
                        SiteStatusDTO.ARCHIVED,
                        SiteTypeDTO.OTHER,
                        WorkspaceSiteMetaStatus.ARCHIVED,
                        WorkspaceSiteMetaType.OTHER
                )
        );
    }

    private SiteUpdatedEvent getSiteUpdatedEvent(
            final String siteId,
            final SiteStatusDTO status,
            final SiteTypeDTO type,
            final String name,
            final String description,
            final String updatedAt
    ) {
        return new SiteUpdatedEvent(siteId, 17L, name, status, type, description, updatedAt);
    }

    private SiteMetaUpdate getSiteMetaUpdate(final WorkspaceSiteMetaStatus status, final WorkspaceSiteMetaType type) {
        return SiteMetaUpdate.builder()
                .siteId(UUID.fromString("55db7314-63a5-49b5-bdb6-6a6cc59e61b9"))
                .userId(17L)
                .name("Site")
                .status(status)
                .type(type)
                .description("Description")
                .updatedAt(Instant.parse("2026-02-18T10:00:00Z"))
                .build();
    }
}
