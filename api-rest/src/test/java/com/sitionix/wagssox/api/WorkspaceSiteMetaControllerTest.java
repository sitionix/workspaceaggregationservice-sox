package com.sitionix.wagssox.api;

import com.sitionix.wagssox.api.dto.WorkspaceSiteMetaDTO;
import com.sitionix.wagssox.api.mapper.WorkspaceSiteMetaApiMapper;
import com.sitionix.wagssox.application.WorkspaceSiteMetaQuery;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkspaceSiteMetaControllerTest {

    private WorkspaceSiteMetaController workspaceSiteMetaController;

    @BeforeEach
    void setUp() {
        this.workspaceSiteMetaController = new WorkspaceSiteMetaController(
                new WorkspaceSiteMetaQuery() {
                    @Override
                    public List<WorkspaceSiteMeta> findByOwnerUserId(final Long ownerUserId) {
                        return WorkspaceSiteMetaControllerTest.this.findByOwnerUserId(ownerUserId);
                    }
                },
                new WorkspaceSiteMetaApiMapper() {
                    @Override
                    public WorkspaceSiteMetaDTO asDto(final WorkspaceSiteMeta workspaceSiteMeta) {
                        return WorkspaceSiteMetaControllerTest.this.asDto(workspaceSiteMeta);
                    }

                    @Override
                    public List<WorkspaceSiteMetaDTO> asDtoList(final List<WorkspaceSiteMeta> workspaceSiteMetas) {
                        return WorkspaceSiteMetaControllerTest.this.asDtoList(workspaceSiteMetas);
                    }
                }
        );
    }

    @Test
    void givenOwnerUserId_whenGetWorkspaceSites_thenReturnMappedSiteMetaList() {
        //given
        final Long ownerUserId = 17L;

        //when
        final List<WorkspaceSiteMetaDTO> actual = this.workspaceSiteMetaController.getWorkspaceSites(ownerUserId);

        //then
        assertThat(actual).hasSize(1);
        assertThat(actual.getFirst().ownerUserId()).isEqualTo(ownerUserId);
        assertThat(actual.getFirst().siteId()).isEqualTo(UUID.fromString("2c4ce9ff-f3f8-4c19-886d-53d1b2f7c61f"));
    }

    private List<WorkspaceSiteMeta> findByOwnerUserId(final Long ownerUserId) {
        return List.of(
                new WorkspaceSiteMeta(
                        UUID.fromString("2c4ce9ff-f3f8-4c19-886d-53d1b2f7c61f"),
                        ownerUserId,
                        "Projected Site",
                        WorkspaceSiteMetaStatus.DRAFT,
                        WorkspaceSiteMetaType.PORTFOLIO,
                        "Site description",
                        Instant.parse("2026-02-18T10:00:00Z"),
                        Instant.parse("2026-02-18T10:00:00Z")
                )
        );
    }

    private WorkspaceSiteMetaDTO asDto(final WorkspaceSiteMeta siteMeta) {
        return new WorkspaceSiteMetaDTO(
                siteMeta.siteId(),
                siteMeta.ownerUserId(),
                siteMeta.name(),
                siteMeta.status().name(),
                siteMeta.type().name(),
                siteMeta.description(),
                OffsetDateTime.ofInstant(siteMeta.createdAt(), ZoneOffset.UTC),
                OffsetDateTime.ofInstant(siteMeta.updatedAt(), ZoneOffset.UTC)
        );
    }

    private List<WorkspaceSiteMetaDTO> asDtoList(final List<WorkspaceSiteMeta> siteMetas) {
        return siteMetas.stream()
                .map(this::asDto)
                .toList();
    }
}
