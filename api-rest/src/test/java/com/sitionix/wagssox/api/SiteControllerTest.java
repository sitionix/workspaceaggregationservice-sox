package com.sitionix.wagssox.api;

import com.app_afesox.wagssox.api_first.dto.SiteOverviewDTO;
import com.app_afesox.wagssox.api_first.dto.WorkspaceSitesPageDTO;
import com.sitionix.wagssox.api.mapper.SiteApiMapper;
import com.sitionix.wagssox.domain.SiteOverview;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.usecase.GetSiteOverview;
import com.sitionix.wagssox.domain.usecase.GetWorkspaceSites;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SiteControllerTest {

    @Mock
    private SiteApiMapper siteApiMapper;

    @Mock
    private GetWorkspaceSites getWorkspaceSites;

    @Mock
    private GetSiteOverview getSiteOverview;

    private SiteController siteController;

    @BeforeEach
    void setUp() {
        this.siteController = new SiteController(this.siteApiMapper, this.getWorkspaceSites, this.getSiteOverview);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.siteApiMapper, this.getWorkspaceSites, this.getSiteOverview);
    }

    @Test
    void givenValidRequestParams_whenGetSites_thenReturnWorkspaceSitesPageDto() {
        //given
        final Integer page = 1;
        final Integer size = 20;
        final WorkspaceSitesPage useCaseResult = mock(WorkspaceSitesPage.class);
        final WorkspaceSitesPageDTO responseDTO = mock(WorkspaceSitesPageDTO.class);
        when(this.getWorkspaceSites.execute(any(Pageable.class))).thenReturn(useCaseResult);
        when(this.siteApiMapper.asWorkspaceSitesPageDTO(useCaseResult)).thenReturn(responseDTO);

        //when
        final ResponseEntity<WorkspaceSitesPageDTO> actual = this.siteController.getSites(page, size);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDTO));
        final ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(this.getWorkspaceSites).execute(pageableCaptor.capture());
        final Pageable pageable = pageableCaptor.getValue();
        assertThat(pageable.getPageNumber()).isEqualTo(page);
        assertThat(pageable.getPageSize()).isEqualTo(size);
        assertThat(pageable.getSort().getOrderFor("updatedAt").isDescending()).isTrue();
        verify(this.siteApiMapper).asWorkspaceSitesPageDTO(useCaseResult);
    }

    @Test
    void givenSiteId_whenGetSiteOverview_thenReturnSiteOverviewDto() {
        //given
        final UUID siteId = UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003");
        final SiteOverview useCaseResult = mock(SiteOverview.class);
        final SiteOverviewDTO responseDTO = mock(SiteOverviewDTO.class);
        when(this.getSiteOverview.execute(siteId)).thenReturn(useCaseResult);
        when(this.siteApiMapper.asSiteOverviewDTO(useCaseResult)).thenReturn(responseDTO);

        //when
        final ResponseEntity<SiteOverviewDTO> actual = this.siteController.getSiteOverview(siteId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDTO));
        verify(this.getSiteOverview).execute(siteId);
        verify(this.siteApiMapper).asSiteOverviewDTO(useCaseResult);
    }
}
