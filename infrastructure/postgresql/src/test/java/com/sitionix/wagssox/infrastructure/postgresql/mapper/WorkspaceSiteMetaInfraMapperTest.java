package com.sitionix.wagssox.infrastructure.postgresql.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaStatusEntity;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaTypeEntity;
import java.time.Instant;
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
class WorkspaceSiteMetaInfraMapperTest {

    @Mock
    private WorkspaceSiteMetaStatusInfraMapper workspaceSiteMetaStatusInfraMapper;

    @Mock
    private WorkspaceSiteMetaTypeInfraMapper workspaceSiteMetaTypeInfraMapper;

    private WorkspaceSiteMetaInfraMapper workspaceSiteMetaInfraMapper;

    @BeforeEach
    void setUp() {
        this.workspaceSiteMetaInfraMapper = new WorkspaceSiteMetaInfraMapperImpl(
                this.workspaceSiteMetaStatusInfraMapper,
                this.workspaceSiteMetaTypeInfraMapper
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.workspaceSiteMetaStatusInfraMapper, this.workspaceSiteMetaTypeInfraMapper);
    }

    @Test
    void givenDomainModel_whenAsEntity_thenReturnEntity() {
        //given
        final WorkspaceSiteMeta siteMeta = this.getWorkspaceSiteMeta();
        final WorkspaceSiteMetaStatusEntity mappedStatus = this.getStatusEntity(2L, "PUBLISHED");
        final WorkspaceSiteMetaTypeEntity mappedType = this.getTypeEntity(2L, "BUSINESS");
        final WorkspaceSiteMetaEntity expected = this.getWorkspaceSiteMetaEntity(mappedStatus, mappedType);

        when(this.workspaceSiteMetaStatusInfraMapper.asStatusEntity(WorkspaceSiteMetaStatus.PUBLISHED)).thenReturn(mappedStatus);
        when(this.workspaceSiteMetaTypeInfraMapper.asTypeEntity(WorkspaceSiteMetaType.BUSINESS)).thenReturn(mappedType);

        //when
        final WorkspaceSiteMetaEntity actual = this.workspaceSiteMetaInfraMapper.asEntity(siteMeta);

        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(this.workspaceSiteMetaStatusInfraMapper).asStatusEntity(WorkspaceSiteMetaStatus.PUBLISHED);
        verify(this.workspaceSiteMetaTypeInfraMapper).asTypeEntity(WorkspaceSiteMetaType.BUSINESS);
    }

    @Test
    void givenEntity_whenAsDomain_thenReturnDomainModel() {
        //given
        final WorkspaceSiteMetaStatusEntity statusEntity = this.getStatusEntity(2L, "PUBLISHED");
        final WorkspaceSiteMetaTypeEntity typeEntity = this.getTypeEntity(2L, "BUSINESS");
        final WorkspaceSiteMetaEntity siteMetaEntity = this.getWorkspaceSiteMetaEntity(statusEntity, typeEntity);
        final WorkspaceSiteMeta expected = this.getWorkspaceSiteMeta();

        when(this.workspaceSiteMetaStatusInfraMapper.asStatus(statusEntity)).thenReturn(WorkspaceSiteMetaStatus.PUBLISHED);
        when(this.workspaceSiteMetaTypeInfraMapper.asType(typeEntity)).thenReturn(WorkspaceSiteMetaType.BUSINESS);

        //when
        final WorkspaceSiteMeta actual = this.workspaceSiteMetaInfraMapper.asDomain(siteMetaEntity);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.workspaceSiteMetaStatusInfraMapper).asStatus(statusEntity);
        verify(this.workspaceSiteMetaTypeInfraMapper).asType(typeEntity);
    }

    @Test
    void givenNullDomainModel_whenAsEntity_thenReturnNull() {
        //given
        final WorkspaceSiteMeta siteMeta = null;

        //when
        final WorkspaceSiteMetaEntity actual = this.workspaceSiteMetaInfraMapper.asEntity(siteMeta);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenNullEntity_whenAsDomain_thenReturnNull() {
        //given
        final WorkspaceSiteMetaEntity siteMetaEntity = null;

        //when
        final WorkspaceSiteMeta actual = this.workspaceSiteMetaInfraMapper.asDomain(siteMetaEntity);

        //then
        assertThat(actual).isNull();
    }

    private WorkspaceSiteMeta getWorkspaceSiteMeta() {
        return WorkspaceSiteMeta.builder()
                .siteId(UUID.fromString("9a17fa0a-c958-4fa1-a91f-c90f6db9694f"))
                .userId(17L)
                .name("Site")
                .status(WorkspaceSiteMetaStatus.PUBLISHED)
                .type(WorkspaceSiteMetaType.BUSINESS)
                .description("Description")
                .createdAt(Instant.parse("2026-02-19T09:00:00Z"))
                .updatedAt(Instant.parse("2026-02-19T11:00:00Z"))
                .deletedAt(Instant.parse("2026-02-20T11:00:00Z"))
                .build();
    }

    private WorkspaceSiteMetaEntity getWorkspaceSiteMetaEntity(
            final WorkspaceSiteMetaStatusEntity statusEntity,
            final WorkspaceSiteMetaTypeEntity typeEntity
    ) {
        return new WorkspaceSiteMetaEntity(
                UUID.fromString("9a17fa0a-c958-4fa1-a91f-c90f6db9694f"),
                17L,
                "Site",
                statusEntity,
                typeEntity,
                "Description",
                Instant.parse("2026-02-19T09:00:00Z"),
                Instant.parse("2026-02-19T11:00:00Z"),
                Instant.parse("2026-02-20T11:00:00Z")
        );
    }

    private WorkspaceSiteMetaStatusEntity getStatusEntity(final Long id, final String description) {
        return WorkspaceSiteMetaStatusEntity.builder()
                .id(id)
                .description(description)
                .build();
    }

    private WorkspaceSiteMetaTypeEntity getTypeEntity(final Long id, final String description) {
        return WorkspaceSiteMetaTypeEntity.builder()
                .id(id)
                .description(description)
                .build();
    }
}
