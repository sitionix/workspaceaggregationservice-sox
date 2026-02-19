package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import java.time.Instant;
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
class GetWorkspaceSitesImplTest {

    @Mock
    private WorkspaceSiteMetaRepository workspaceSiteMetaRepository;

    private GetWorkspaceSitesImpl getWorkspaceSites;

    @BeforeEach
    void setUp() {
        this.getWorkspaceSites = new GetWorkspaceSitesImpl(this.workspaceSiteMetaRepository);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.workspaceSiteMetaRepository);
    }

    @Test
    void givenTwentyOneActiveSitesWithNullName_whenExecute_thenReturnFirstTwentyWithHasNextTrueAndNameFallback() {
        //given
        final Long userId = 123L;
        final Integer page = 0;
        final Integer size = 20;
        final List<WorkspaceSiteMeta> repositoryResponse = this.getWorkspaceSiteMetas(21, null);
        final List<WorkspaceSiteMeta> expectedItems = this.getWorkspaceSiteMetas(20, "Untitled site");
        final WorkspaceSitesPage expected = WorkspaceSitesPage.builder()
                .items(expectedItems)
                .page(page)
                .size(size)
                .hasNext(true)
                .build();
        when(this.workspaceSiteMetaRepository.findActiveByUserId(userId, page, size + 1)).thenReturn(repositoryResponse);

        //when
        final WorkspaceSitesPage actual = this.getWorkspaceSites.execute(userId, page, size);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.workspaceSiteMetaRepository).findActiveByUserId(userId, page, size + 1);
    }

    @Test
    void givenFiveActiveSites_whenExecute_thenReturnFiveWithHasNextFalse() {
        //given
        final Long userId = 456L;
        final Integer page = 1;
        final Integer size = 20;
        final List<WorkspaceSiteMeta> repositoryResponse = this.getWorkspaceSiteMetas(5, "Site");
        final WorkspaceSitesPage expected = WorkspaceSitesPage.builder()
                .items(repositoryResponse)
                .page(page)
                .size(size)
                .hasNext(false)
                .build();
        when(this.workspaceSiteMetaRepository.findActiveByUserId(userId, page, size + 1)).thenReturn(repositoryResponse);

        //when
        final WorkspaceSitesPage actual = this.getWorkspaceSites.execute(userId, page, size);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.workspaceSiteMetaRepository).findActiveByUserId(userId, page, size + 1);
    }

    private List<WorkspaceSiteMeta> getWorkspaceSiteMetas(final Integer count, final String name) {
        return java.util.stream.IntStream.range(0, count)
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
