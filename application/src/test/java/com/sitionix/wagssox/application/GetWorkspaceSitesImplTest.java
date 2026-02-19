package com.sitionix.wagssox.application;

import com.sitionix.forge.security.server.user.ForgeUserClient;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.exception.AuthenticationRequiredException;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetWorkspaceSitesImplTest {

    @Mock
    private WorkspaceSiteMetaRepository workspaceSiteMetaRepository;

    @Mock
    private ForgeUserClient forgeUserClient;

    private GetWorkspaceSitesImpl getWorkspaceSites;

    @BeforeEach
    void setUp() {
        this.getWorkspaceSites = new GetWorkspaceSitesImpl(this.workspaceSiteMetaRepository, this.forgeUserClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.workspaceSiteMetaRepository, this.forgeUserClient);
    }

    @Test
    void givenWorkspaceSitesPage_whenExecute_thenReturnWorkspaceSitesPageFromRepository() {
        //given
        final Long userId = 123L;
        final Integer page = 0;
        final Integer size = 20;
        final WorkspaceSiteMeta workspaceSiteMeta = mock(WorkspaceSiteMeta.class);
        final WorkspaceSitesPage repositoryResponse = WorkspaceSitesPage.builder()
                .items(List.of(workspaceSiteMeta))
                .page(page)
                .size(size)
                .hasNext(true)
                .build();
        when(this.forgeUserClient.getUserId()).thenReturn(userId);
        when(this.workspaceSiteMetaRepository.findActiveByUserId(userId, page, size)).thenReturn(repositoryResponse);

        //when
        final WorkspaceSitesPage actual = this.getWorkspaceSites.execute(page, size);

        //then
        assertThat(actual).isEqualTo(repositoryResponse);
        verify(this.forgeUserClient).getUserId();
        verify(this.workspaceSiteMetaRepository).findActiveByUserId(userId, page, size);
    }

    @Test
    void givenSecondPage_whenExecute_thenReturnSecondPageFromRepository() {
        //given
        final Long userId = 456L;
        final Integer page = 1;
        final Integer size = 20;
        final WorkspaceSiteMeta workspaceSiteMeta = mock(WorkspaceSiteMeta.class);
        final WorkspaceSitesPage repositoryResponse = WorkspaceSitesPage.builder()
                .items(List.of(workspaceSiteMeta))
                .page(page)
                .size(size)
                .hasNext(false)
                .build();
        when(this.forgeUserClient.getUserId()).thenReturn(userId);
        when(this.workspaceSiteMetaRepository.findActiveByUserId(userId, page, size)).thenReturn(repositoryResponse);

        //when
        final WorkspaceSitesPage actual = this.getWorkspaceSites.execute(page, size);

        //then
        assertThat(actual).isEqualTo(repositoryResponse);
        verify(this.forgeUserClient).getUserId();
        verify(this.workspaceSiteMetaRepository).findActiveByUserId(userId, page, size);
    }

    @Test
    void givenMissingUserContext_whenExecute_thenThrowAuthenticationRequiredException() {
        //given
        final Integer page = 0;
        final Integer size = 20;
        when(this.forgeUserClient.getUserId()).thenThrow(new RuntimeException("Missing user"));

        //when then
        assertThatThrownBy(() -> this.getWorkspaceSites.execute(page, size))
                .isInstanceOf(AuthenticationRequiredException.class)
                .hasMessage("Authentication required");

        verify(this.forgeUserClient).getUserId();
    }
}
