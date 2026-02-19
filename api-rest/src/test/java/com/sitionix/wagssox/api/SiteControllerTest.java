package com.sitionix.wagssox.api;

import com.app_afesox.wagssox.api_first.dto.WorkspaceSitesPageDTO;
import com.sitionix.wagssox.api.mapper.SiteApiMapper;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.usecase.GetWorkspaceSites;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
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

    private SiteController siteController;

    @BeforeEach
    void setUp() {
        this.siteController = new SiteController(this.siteApiMapper, this.getWorkspaceSites);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.siteApiMapper, this.getWorkspaceSites);
    }

    @Test
    void givenValidRequestParams_whenGetSites_thenReturnWorkspaceSitesPageDto() {
        //given
        final Long userId = 123L;
        final Integer size = 20;
        final Integer page = 1;
        final WorkspaceSitesPage useCaseResult = mock(WorkspaceSitesPage.class);
        final WorkspaceSitesPageDTO responseDTO = mock(WorkspaceSitesPageDTO.class);
        when(this.getWorkspaceSites.execute(userId, page, size)).thenReturn(useCaseResult);
        when(this.siteApiMapper.asWorkspaceSitesPageDTO(useCaseResult)).thenReturn(responseDTO);

        //when
        final ResponseEntity<WorkspaceSitesPageDTO> actual = this.siteController.getSites(userId, size, page);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDTO));
        verify(this.getWorkspaceSites).execute(userId, page, size);
        verify(this.siteApiMapper).asWorkspaceSitesPageDTO(useCaseResult);
    }
}
