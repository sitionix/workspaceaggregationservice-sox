package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaSlice;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.exception.AuthenticationRequiredException;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import com.sitionix.forge.security.server.user.ForgeUserClient;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void givenActiveSitesSliceWithNullName_whenExecute_thenReturnItemsWithHasNextTrueAndNameFallback() {
        //given
        final Long userId = 123L;
        final Integer page = 0;
        final Integer size = 20;
        final WorkspaceSiteMetaSlice repositoryResponse = WorkspaceSiteMetaSlice.builder()
                .items(this.getWorkspaceSiteMetas(2, null))
                .hasNext(true)
                .build();
        final List<WorkspaceSiteMeta> expectedItems = this.getWorkspaceSiteMetas(2, "Untitled site");
        final WorkspaceSitesPage expected = WorkspaceSitesPage.builder()
                .items(expectedItems)
                .page(page)
                .size(size)
                .hasNext(true)
                .build();
        when(this.forgeUserClient.getUserId()).thenReturn(userId);
        when(this.workspaceSiteMetaRepository.findActiveByUserId(userId, page, size)).thenReturn(repositoryResponse);

        //when
        final WorkspaceSitesPage actual = this.getWorkspaceSites.execute(page, size);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.forgeUserClient).getUserId();
        verify(this.workspaceSiteMetaRepository).findActiveByUserId(userId, page, size);
    }

    @Test
    void givenActiveSitesSlice_whenExecute_thenReturnItemsWithHasNextFalse() {
        //given
        final Long userId = 456L;
        final Integer page = 1;
        final Integer size = 20;
        final WorkspaceSiteMetaSlice repositoryResponse = WorkspaceSiteMetaSlice.builder()
                .items(this.getWorkspaceSiteMetas(5, "Site"))
                .hasNext(false)
                .build();
        final WorkspaceSitesPage expected = WorkspaceSitesPage.builder()
                .items(repositoryResponse.getItems())
                .page(page)
                .size(size)
                .hasNext(false)
                .build();
        when(this.forgeUserClient.getUserId()).thenReturn(userId);
        when(this.workspaceSiteMetaRepository.findActiveByUserId(userId, page, size)).thenReturn(repositoryResponse);

        //when
        final WorkspaceSitesPage actual = this.getWorkspaceSites.execute(page, size);

        //then
        assertThat(actual).isEqualTo(expected);
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

    private List<WorkspaceSiteMeta> getWorkspaceSiteMetas(final Integer count, final String name) {
        return IntStream.range(0, count)
                .mapToObj(index -> WorkspaceSiteMeta.builder()
                        .siteId(UUID.fromString(String.format("00000000-0000-0000-0000-%012d", index + 1)))
                        .userId(123L)
                        .name(name)
                        .status(WorkspaceSiteMetaStatus.DRAFT)
                        .type(WorkspaceSiteMetaType.PORTFOLIO)
                        .description("Description")
                        .createdAt(Instant.parse("2026-01-10T12:00:00Z"))
                        .updatedAt(Instant.parse("2026-01-29T08:30:00Z"))
                        .build())
                .toList();
    }
}
