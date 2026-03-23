package com.sitionix.wagssox.application;

import com.sitionix.forge.security.server.user.ForgeUserClient;
import com.sitionix.wagssox.domain.SiteOverview;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.domain.exception.SiteOverviewNotFoundException;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetSiteOverviewImplTest {

    @Mock
    private WorkspaceSiteMetaRepository workspaceSiteMetaRepository;

    @Mock
    private ForgeUserClient forgeUserClient;

    private GetSiteOverviewImpl getSiteOverview;

    @BeforeEach
    void setUp() {
        this.getSiteOverview = new GetSiteOverviewImpl(this.workspaceSiteMetaRepository, this.forgeUserClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.workspaceSiteMetaRepository, this.forgeUserClient);
    }

    @Test
    void givenAccessibleSite_whenExecute_thenReturnSiteOverview() {
        //given
        final Long userId = 123L;
        final UUID siteId = UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003");
        final WorkspaceSiteMeta siteMeta = this.getWorkspaceSiteMeta(siteId);
        final SiteOverview expected = this.getSiteOverview(siteId);
        when(this.forgeUserClient.getUserId()).thenReturn(userId);
        when(this.workspaceSiteMetaRepository.findActiveByUserIdAndSiteId(userId, siteId)).thenReturn(Optional.of(siteMeta));

        //when
        final SiteOverview actual = this.getSiteOverview.execute(siteId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.forgeUserClient).getUserId();
        verify(this.workspaceSiteMetaRepository).findActiveByUserIdAndSiteId(userId, siteId);
    }

    @Test
    void givenMissingSite_whenExecute_thenThrowSiteOverviewNotFoundException() {
        //given
        final Long userId = 123L;
        final UUID siteId = UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003");
        when(this.forgeUserClient.getUserId()).thenReturn(userId);
        when(this.workspaceSiteMetaRepository.findActiveByUserIdAndSiteId(userId, siteId)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> this.getSiteOverview.execute(siteId))
                .isInstanceOf(SiteOverviewNotFoundException.class)
                .hasMessage("Site not found");

        verify(this.forgeUserClient).getUserId();
        verify(this.workspaceSiteMetaRepository).findActiveByUserIdAndSiteId(userId, siteId);
    }

    @Test
    void givenMissingUserContext_whenExecute_thenPropagateAuthenticationException() {
        //given
        final UUID siteId = UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003");
        when(this.forgeUserClient.getUserId()).thenThrow(new BadCredentialsException("Authentication required."));

        //when then
        assertThatThrownBy(() -> this.getSiteOverview.execute(siteId))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Authentication required.");

        verify(this.forgeUserClient).getUserId();
    }

    private WorkspaceSiteMeta getWorkspaceSiteMeta(final UUID siteId) {
        return WorkspaceSiteMeta.builder()
                .siteId(siteId)
                .userId(123L)
                .name("Agency Portfolio")
                .status(WorkspaceSiteMetaStatus.DRAFT)
                .type(WorkspaceSiteMetaType.PORTFOLIO)
                .description(null)
                .createdAt(Instant.parse("2026-01-10T12:00:00Z"))
                .updatedAt(Instant.parse("2026-01-29T08:30:00Z"))
                .deletedAt(null)
                .build();
    }

    private SiteOverview getSiteOverview(final UUID siteId) {
        return SiteOverview.builder()
                .siteId(siteId)
                .name("Agency Portfolio")
                .status(WorkspaceSiteMetaStatus.DRAFT)
                .type(WorkspaceSiteMetaType.PORTFOLIO)
                .description(null)
                .createdAt(Instant.parse("2026-01-10T12:00:00Z"))
                .updatedAt(Instant.parse("2026-01-29T08:30:00Z"))
                .build();
    }
}
