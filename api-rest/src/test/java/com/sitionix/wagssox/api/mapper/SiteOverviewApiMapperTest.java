package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.SiteOverviewDTO;
import com.sitionix.wagssox.domain.SiteOverview;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SiteOverviewApiMapperTest {

    @Mock
    private InstantApiMapper instantApiMapper;

    @Mock
    private SiteOverviewStatusApiMapper siteOverviewStatusApiMapper;

    @Mock
    private SiteOverviewTypeApiMapper siteOverviewTypeApiMapper;

    private SiteOverviewApiMapper siteOverviewApiMapper;

    @BeforeEach
    void setUp() {
        this.siteOverviewApiMapper = new SiteOverviewApiMapperImpl(
                this.instantApiMapper,
                this.siteOverviewStatusApiMapper,
                this.siteOverviewTypeApiMapper
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                this.instantApiMapper,
                this.siteOverviewStatusApiMapper,
                this.siteOverviewTypeApiMapper
        );
    }

    @Test
    void givenSiteOverview_whenAsSiteOverviewDto_thenReturnSiteOverviewDto() {
        //given
        final SiteOverview siteOverview = this.getSiteOverview();
        final SiteOverviewDTO expected = this.getSiteOverviewDto();
        when(this.siteOverviewStatusApiMapper.mapStatus(siteOverview.status()))
                .thenReturn(SiteOverviewDTO.StatusEnum.DRAFT);
        when(this.siteOverviewTypeApiMapper.mapType(siteOverview.type()))
                .thenReturn(SiteOverviewDTO.TypeEnum.PORTFOLIO);
        when(this.instantApiMapper.toUtcOffsetDateTime(siteOverview.createdAt()))
                .thenReturn(OffsetDateTime.parse("2026-01-10T12:00:00Z"));
        when(this.instantApiMapper.toUtcOffsetDateTime(siteOverview.updatedAt()))
                .thenReturn(OffsetDateTime.parse("2026-01-29T08:30:00Z"));

        //when
        final SiteOverviewDTO actual = this.siteOverviewApiMapper.asSiteOverviewDTO(siteOverview);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.siteOverviewStatusApiMapper).mapStatus(siteOverview.status());
        verify(this.siteOverviewTypeApiMapper).mapType(siteOverview.type());
        verify(this.instantApiMapper).toUtcOffsetDateTime(siteOverview.createdAt());
        verify(this.instantApiMapper).toUtcOffsetDateTime(siteOverview.updatedAt());
    }

    private SiteOverview getSiteOverview() {
        return SiteOverview.builder()
                .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .name("Portfolio")
                .status(WorkspaceSiteMetaStatus.DRAFT)
                .type(WorkspaceSiteMetaType.PORTFOLIO)
                .description(null)
                .createdAt(Instant.parse("2026-01-10T12:00:00Z"))
                .updatedAt(Instant.parse("2026-01-29T08:30:00Z"))
                .build();
    }

    private SiteOverviewDTO getSiteOverviewDto() {
        return SiteOverviewDTO.builder()
                .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .name("Portfolio")
                .status(SiteOverviewDTO.StatusEnum.DRAFT)
                .type(SiteOverviewDTO.TypeEnum.PORTFOLIO)
                .description(null)
                .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"))
                .build();
    }
}
