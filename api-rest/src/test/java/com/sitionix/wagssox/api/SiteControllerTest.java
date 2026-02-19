package com.sitionix.wagssox.api;

import com.sitionix.wagssox.api.dto.WorkspaceSitesResponseDTO;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void givenValidRequestParams_whenGetSites_thenReturnWorkspaceSitesResponseDto() {
        //given
        final Long userId = 123L;
        final Integer page = 0;
        final Integer size = 20;
        final WorkspaceSitesPage useCaseResult = mock(WorkspaceSitesPage.class);
        final WorkspaceSitesResponseDTO responseDTO = mock(WorkspaceSitesResponseDTO.class);
        when(this.getWorkspaceSites.execute(userId, page, size)).thenReturn(useCaseResult);
        when(this.siteApiMapper.asWorkspaceSitesResponseDTO(useCaseResult)).thenReturn(responseDTO);

        //when
        final ResponseEntity<WorkspaceSitesResponseDTO> actual = this.siteController.getSites(userId, page, size);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDTO));
        verify(this.getWorkspaceSites).execute(userId, page, size);
        verify(this.siteApiMapper).asWorkspaceSitesResponseDTO(useCaseResult);
    }

    @Test
    void givenMissingUserId_whenGetSites_thenThrowIllegalArgumentException() {
        //given
        final Long userId = null;
        final Integer page = 0;
        final Integer size = 20;

        //when then
        assertThatThrownBy(() -> this.siteController.getSites(userId, page, size))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("userId is required");
    }

    @Test
    void givenInvalidSize_whenGetSites_thenThrowIllegalArgumentException() {
        //given
        final Long userId = 123L;
        final Integer page = 0;
        final Integer size = 10;

        //when then
        assertThatThrownBy(() -> this.siteController.getSites(userId, page, size))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("size must be 20");
    }

    @Test
    void givenNegativePage_whenGetSites_thenThrowIllegalArgumentException() {
        //given
        final Long userId = 123L;
        final Integer page = -1;
        final Integer size = 20;

        //when then
        assertThatThrownBy(() -> this.siteController.getSites(userId, page, size))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("page must be >= 0");
    }
}
