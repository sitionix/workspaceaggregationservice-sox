package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.WorkspaceSiteCardDTO;
import com.app_afesox.wagssox.api_first.dto.WorkspaceSitesPageDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;

import static org.assertj.core.api.Assertions.assertThat;

class SiteApiMapperTest {

    private SiteApiMapper siteApiMapper;

    @BeforeEach
    void setUp() {
        this.siteApiMapper = new SiteApiMapperImpl();
    }

    @Test
    void givenWorkspaceSitesPage_whenAsWorkspaceSitesPageDto_thenReturnWorkspaceSitesPageDto() {
        //given
        final WorkspaceSitesPage workspaceSitesPage = this.getWorkspaceSitesPage();
        final WorkspaceSitesPageDTO expected = this.getWorkspaceSitesPageDTO();

        //when
        final WorkspaceSitesPageDTO actual = this.siteApiMapper.asWorkspaceSitesPageDTO(workspaceSitesPage);

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

    private WorkspaceSitesPageDTO getWorkspaceSitesPageDTO() {
        return WorkspaceSitesPageDTO.builder()
                .items(List.of(WorkspaceSiteCardDTO.builder()
                        .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                        .name("Portfolio")
                        .status(WorkspaceSiteCardDTO.StatusEnum.DRAFT)
                        .type(JsonNullable.of(WorkspaceSiteCardDTO.TypeEnum.PORTFOLIO))
                        .description(JsonNullable.of(null))
                        .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                        .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"))
                        .build()))
                .page(0)
                .size(WorkspaceSitesPageDTO.SizeEnum.NUMBER_20)
                .hasNext(true)
                .build();
    }
}
