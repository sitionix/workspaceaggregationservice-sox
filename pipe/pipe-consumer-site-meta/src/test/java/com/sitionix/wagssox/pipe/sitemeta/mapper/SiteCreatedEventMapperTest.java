package com.sitionix.wagssox.pipe.sitemeta.mapper;

import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteStatusDTO;
import com.app_afesox.stsssox.events.sitemeta.SiteTypeDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
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

class SiteCreatedEventMapperTest {

    private SiteCreatedEventMapper siteCreatedEventMapper;

    @BeforeEach
    void setUp() {
        this.siteCreatedEventMapper = new SiteCreatedEventMapperImpl();
    }

    @Test
    void givenNullPayload_whenAsProjection_thenReturnNull() {
        //given
        final SiteCreatedEvent payload = null;

        //when
        final WorkspaceSiteMeta actual = this.siteCreatedEventMapper.asProjection(payload);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenMapper_whenPayloadType_thenReturnSiteCreatedEventClass() {
        //given
        final Class<SiteCreatedEvent> expected = SiteCreatedEvent.class;

        //when
        final Class<SiteCreatedEvent> actual = this.siteCreatedEventMapper.payloadType();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenMapper_whenResultType_thenReturnWorkspaceSiteMetaClass() {
        //given
        final Class<WorkspaceSiteMeta> expected = WorkspaceSiteMeta.class;

        //when
        final Class<WorkspaceSiteMeta> actual = this.siteCreatedEventMapper.resultType();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("siteCreatedEnums")
    void givenSiteCreatedEventWithStatusAndType_whenAsProjection_thenReturnProjection(
            final SiteStatusDTO status,
            final SiteTypeDTO type,
            final WorkspaceSiteMetaStatus expectedStatus,
            final WorkspaceSiteMetaType expectedType
    ) {
        //given
        final SiteCreatedEvent payload = this.getSiteCreatedEvent(
                "55db7314-63a5-49b5-bdb6-6a6cc59e61b9",
                status,
                type,
                "Site",
                "Description",
                "2026-02-18T09:00:00Z",
                "2026-02-18T10:00:00Z"
        );
        final WorkspaceSiteMeta expected = this.getWorkspaceSiteMeta(expectedStatus, expectedType);

        //when
        final WorkspaceSiteMeta actual = this.siteCreatedEventMapper.asProjection(payload);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenSiteCreatedEventWithNullOptionalFields_whenAsProjection_thenReturnProjectionWithNulls() {
        //given
        final SiteCreatedEvent payload = this.getSiteCreatedEvent(
                "55db7314-63a5-49b5-bdb6-6a6cc59e61b9",
                null,
                null,
                null,
                null,
                "2026-02-18T09:00:00Z",
                "2026-02-18T10:00:00Z"
        );
        final WorkspaceSiteMeta expected = WorkspaceSiteMeta.builder()
                .siteId(UUID.fromString("55db7314-63a5-49b5-bdb6-6a6cc59e61b9"))
                .userId(17L)
                .name(null)
                .status(null)
                .type(null)
                .description(null)
                .createdAt(Instant.parse("2026-02-18T09:00:00Z"))
                .updatedAt(Instant.parse("2026-02-18T10:00:00Z"))
                .build();

        //when
        final WorkspaceSiteMeta actual = this.siteCreatedEventMapper.asProjection(payload);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> siteCreatedEnums() {
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

    private SiteCreatedEvent getSiteCreatedEvent(
            final String siteId,
            final SiteStatusDTO status,
            final SiteTypeDTO type,
            final String name,
            final String description,
            final String createdAt,
            final String updatedAt
    ) {
        return new SiteCreatedEvent(siteId, 17L, name, status, type, description, createdAt, updatedAt);
    }

    private WorkspaceSiteMeta getWorkspaceSiteMeta(final WorkspaceSiteMetaStatus status, final WorkspaceSiteMetaType type) {
        return WorkspaceSiteMeta.builder()
                .siteId(UUID.fromString("55db7314-63a5-49b5-bdb6-6a6cc59e61b9"))
                .userId(17L)
                .name("Site")
                .status(status)
                .type(type)
                .description("Description")
                .createdAt(Instant.parse("2026-02-18T09:00:00Z"))
                .updatedAt(Instant.parse("2026-02-18T10:00:00Z"))
                .build();
    }
}
