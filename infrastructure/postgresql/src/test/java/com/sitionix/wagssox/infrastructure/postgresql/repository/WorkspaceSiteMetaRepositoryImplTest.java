package com.sitionix.wagssox.infrastructure.postgresql.repository;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import com.sitionix.wagssox.infrastructure.postgresql.jpa.WorkspaceSiteMetaJpaRepository;
import com.sitionix.wagssox.infrastructure.postgresql.mapper.WorkspaceSiteMetaInfraMapper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkspaceSiteMetaRepositoryImplTest {

    @Mock
    private WorkspaceSiteMetaJpaRepository workspaceSiteMetaJpaRepository;

    @Mock
    private WorkspaceSiteMetaInfraMapper workspaceSiteMetaInfraMapper;

    private WorkspaceSiteMetaRepositoryImpl workspaceSiteMetaRepository;

    @BeforeEach
    void setUp() {
        this.workspaceSiteMetaRepository = new WorkspaceSiteMetaRepositoryImpl(
                this.workspaceSiteMetaJpaRepository,
                this.workspaceSiteMetaInfraMapper
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.workspaceSiteMetaJpaRepository, this.workspaceSiteMetaInfraMapper);
    }

    @Test
    void givenSiteMeta_whenSave_thenReturnSavedDomainModel() {
        //given
        final WorkspaceSiteMeta siteMeta = mock(WorkspaceSiteMeta.class);
        final WorkspaceSiteMetaEntity entity = mock(WorkspaceSiteMetaEntity.class);
        final WorkspaceSiteMetaEntity savedEntity = mock(WorkspaceSiteMetaEntity.class);
        final WorkspaceSiteMeta expected = mock(WorkspaceSiteMeta.class);
        when(this.workspaceSiteMetaInfraMapper.asEntity(siteMeta)).thenReturn(entity);
        when(this.workspaceSiteMetaJpaRepository.save(entity)).thenReturn(savedEntity);
        when(this.workspaceSiteMetaInfraMapper.asDomain(savedEntity)).thenReturn(expected);

        //when
        final WorkspaceSiteMeta actual = this.workspaceSiteMetaRepository.save(siteMeta);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.workspaceSiteMetaInfraMapper).asEntity(siteMeta);
        verify(this.workspaceSiteMetaJpaRepository).save(entity);
        verify(this.workspaceSiteMetaInfraMapper).asDomain(savedEntity);
    }

    @Test
    void givenExistingSiteId_whenFindBySiteId_thenReturnMappedDomainModel() {
        //given
        final UUID siteId = UUID.fromString("a4daef31-b03a-4e90-9fe9-297c74eaf628");
        final WorkspaceSiteMetaEntity entity = mock(WorkspaceSiteMetaEntity.class);
        final WorkspaceSiteMeta expected = mock(WorkspaceSiteMeta.class);
        when(this.workspaceSiteMetaJpaRepository.findById(siteId)).thenReturn(Optional.of(entity));
        when(this.workspaceSiteMetaInfraMapper.asDomain(entity)).thenReturn(expected);

        //when
        final Optional<WorkspaceSiteMeta> actual = this.workspaceSiteMetaRepository.findBySiteId(siteId);

        //then
        assertThat(actual).contains(expected);
        verify(this.workspaceSiteMetaJpaRepository).findById(siteId);
        verify(this.workspaceSiteMetaInfraMapper).asDomain(entity);
    }

    @Test
    void givenUserIdAndPageable_whenFindActiveByUserId_thenQueryWithArchivedFilterAndMapPage() {
        //given
        final Long userId = 123L;
        final Pageable pageable = PageRequest.of(1, 20);
        final Page<WorkspaceSiteMetaEntity> entityPage = mock(Page.class);
        final WorkspaceSitesPage expected = mock(WorkspaceSitesPage.class);
        when(this.workspaceSiteMetaJpaRepository.findActiveByUserId(
                eq(userId),
                eq(3L),
                eq(pageable)
        )).thenReturn(entityPage);
        when(this.workspaceSiteMetaInfraMapper.asWorkspaceSitesPage(entityPage)).thenReturn(expected);

        //when
        final WorkspaceSitesPage actual = this.workspaceSiteMetaRepository.findActiveByUserId(userId, pageable);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.workspaceSiteMetaJpaRepository).findActiveByUserId(
                eq(userId),
                eq(3L),
                eq(pageable)
        );
        verify(this.workspaceSiteMetaInfraMapper).asWorkspaceSitesPage(entityPage);
    }
}
