package com.sitionix.wagssox.infrastructure.postgresql.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaStatusEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkspaceSiteMetaStatusInfraMapperTest {

    private WorkspaceSiteMetaStatusInfraMapper workspaceSiteMetaStatusInfraMapper;

    @BeforeEach
    void setUp() {
        this.workspaceSiteMetaStatusInfraMapper = new WorkspaceSiteMetaStatusInfraMapperImpl();
    }

    @Test
    void givenStatusEntity_whenAsStatus_thenReturnStatus() {
        //given
        final WorkspaceSiteMetaStatusEntity statusEntity = this.getStatusEntity(1L, "DRAFT");

        //when
        final WorkspaceSiteMetaStatus actual = this.workspaceSiteMetaStatusInfraMapper.asStatus(statusEntity);

        //then
        assertThat(actual).isEqualTo(WorkspaceSiteMetaStatus.DRAFT);
    }

    @Test
    void givenNullStatusEntity_whenAsStatus_thenReturnNull() {
        //given
        final WorkspaceSiteMetaStatusEntity statusEntity = null;

        //when
        final WorkspaceSiteMetaStatus actual = this.workspaceSiteMetaStatusInfraMapper.asStatus(statusEntity);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenStatus_whenAsStatusEntity_thenReturnStatusEntity() {
        //given
        final WorkspaceSiteMetaStatus status = WorkspaceSiteMetaStatus.PUBLISHED;
        final WorkspaceSiteMetaStatusEntity expected = this.getStatusEntity(2L, "PUBLISHED");

        //when
        final WorkspaceSiteMetaStatusEntity actual = this.workspaceSiteMetaStatusInfraMapper.asStatusEntity(status);

        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenNullStatus_whenAsStatusEntity_thenReturnNull() {
        //given
        final WorkspaceSiteMetaStatus status = null;

        //when
        final WorkspaceSiteMetaStatusEntity actual = this.workspaceSiteMetaStatusInfraMapper.asStatusEntity(status);

        //then
        assertThat(actual).isNull();
    }

    private WorkspaceSiteMetaStatusEntity getStatusEntity(final Long id, final String description) {
        return WorkspaceSiteMetaStatusEntity.builder()
                .id(id)
                .description(description)
                .build();
    }
}
