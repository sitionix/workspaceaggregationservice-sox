package com.sitionix.wagssox.api.mapper;

import com.sitionix.wagssox.api.dto.WorkspaceSiteCardResponseDTO;
import com.sitionix.wagssox.api.dto.WorkspaceSitesResponseDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SiteApiMapperTest {

    private SiteApiMapper siteApiMapper;

    @BeforeEach
    void setUp() {
        this.siteApiMapper = new SiteApiMapper();
    }

    @Test
    void givenWorkspaceSitesPage_whenAsWorkspaceSitesResponseDto_thenReturnWorkspaceSitesResponseDto() {
        //given
        final WorkspaceSitesPage workspaceSitesPage = this.getWorkspaceSitesPage();
        final WorkspaceSitesResponseDTO expected = this.getWorkspaceSitesResponseDTO();

        //when
        final WorkspaceSitesResponseDTO actual = this.siteApiMapper.asWorkspaceSitesResponseDTO(workspaceSitesPage);

        //then
        assertThat(actual).isEqualTo(expected);
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

    private WorkspaceSitesResponseDTO getWorkspaceSitesResponseDTO() {
        return WorkspaceSitesResponseDTO.builder()
                .items(List.of(WorkspaceSiteCardResponseDTO.builder()
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
}
