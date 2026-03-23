package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.SiteOverviewDTO;
import com.app_afesox.wagssox.api_first.dto.WorkspaceSiteCardDTO;
import com.app_afesox.wagssox.api_first.dto.WorkspaceSitesPageDTO;
import com.sitionix.wagssox.domain.SiteOverview;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
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
class SiteApiMapperTest {

    @Mock
    private WorkspaceSiteStatusApiMapper workspaceSiteStatusApiMapper;

    @Mock
    private WorkspaceSiteTypeApiMapper workspaceSiteTypeApiMapper;

    @Mock
    private SiteOverviewStatusApiMapper siteOverviewStatusApiMapper;

    @Mock
    private SiteOverviewTypeApiMapper siteOverviewTypeApiMapper;

    private SiteApiMapper siteApiMapper;

    @BeforeEach
    void setUp() {
        this.siteApiMapper = new SiteApiMapperImpl(
                this.siteOverviewStatusApiMapper,
                this.siteOverviewTypeApiMapper,
                this.workspaceSiteStatusApiMapper,
                this.workspaceSiteTypeApiMapper
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                this.workspaceSiteStatusApiMapper,
                this.workspaceSiteTypeApiMapper,
                this.siteOverviewStatusApiMapper,
                this.siteOverviewTypeApiMapper
        );
    }

    @Test
    void givenWorkspaceSitesPage_whenAsWorkspaceSitesPageDto_thenReturnWorkspaceSitesPageDto() {
        //given
        final WorkspaceSitesPage workspaceSitesPage = this.getWorkspaceSitesPage();
        final WorkspaceSitesPageDTO expected = this.getWorkspaceSitesPageDTO();
        final WorkspaceSiteMeta workspaceSiteMeta = workspaceSitesPage.getItems().getFirst();
        when(this.workspaceSiteStatusApiMapper.mapStatus(workspaceSiteMeta.getStatus()))
                .thenReturn(WorkspaceSiteCardDTO.StatusEnum.DRAFT);
        when(this.workspaceSiteTypeApiMapper.mapType(workspaceSiteMeta.getType()))
                .thenReturn(WorkspaceSiteCardDTO.TypeEnum.PORTFOLIO);

        //when
        final WorkspaceSitesPageDTO actual = this.siteApiMapper.asWorkspaceSitesPageDTO(workspaceSitesPage);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.workspaceSiteStatusApiMapper).mapStatus(workspaceSiteMeta.getStatus());
        verify(this.workspaceSiteTypeApiMapper).mapType(workspaceSiteMeta.getType());
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

        //when
        final SiteOverviewDTO actual = this.siteApiMapper.asSiteOverviewDTO(siteOverview);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.siteOverviewStatusApiMapper).mapStatus(siteOverview.status());
        verify(this.siteOverviewTypeApiMapper).mapType(siteOverview.type());
    }

    private WorkspaceSitesPage getWorkspaceSitesPage() {
        return WorkspaceSitesPage.builder()
                .items(List.of(WorkspaceSiteMeta.builder()
                        .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                        .name("Portfolio")
                        .status(WorkspaceSiteMetaStatus.DRAFT)
                        .type(WorkspaceSiteMetaType.PORTFOLIO)
                        .description(null)
                        .createdAt(Instant.parse("2026-01-10T12:00:00Z"))
                        .updatedAt(Instant.parse("2026-01-29T08:30:00Z"))
                        .build()))
                .page(0)
                .size(20)
                .hasNext(true)
                .build();
    }

    private WorkspaceSitesPageDTO getWorkspaceSitesPageDTO() {
        return WorkspaceSitesPageDTO.builder()
                .items(List.of(WorkspaceSiteCardDTO.builder()
                        .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                        .name("Portfolio")
                        .status(WorkspaceSiteCardDTO.StatusEnum.DRAFT)
                        .type(WorkspaceSiteCardDTO.TypeEnum.PORTFOLIO)
                        .description(null)
                        .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                        .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"))
                        .build()))
                .page(0)
                .size(20)
                .hasNext(true)
                .build();
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
