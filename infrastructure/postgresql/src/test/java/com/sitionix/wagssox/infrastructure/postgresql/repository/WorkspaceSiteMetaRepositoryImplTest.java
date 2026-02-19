package com.sitionix.wagssox.infrastructure.postgresql.repository;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import com.sitionix.wagssox.infrastructure.postgresql.jpa.WorkspaceSiteMetaJpaRepository;
import com.sitionix.wagssox.infrastructure.postgresql.mapper.WorkspaceSiteMetaInfraMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    void givenUserIdAndPage_whenFindActiveByUserId_thenQueryWithArchivedFilterAndUpdatedAtDesc() {
        //given
        final Long userId = 123L;
        final Integer page = 1;
        final Integer size = 20;
        final WorkspaceSiteMetaEntity entity = mock(WorkspaceSiteMetaEntity.class);
        final WorkspaceSiteMeta mapped = WorkspaceSiteMeta.builder().name(null).build();
        final WorkspaceSiteMeta expectedSite = WorkspaceSiteMeta.builder().name("Untitled site").build();
        final WorkspaceSitesPage expected = WorkspaceSitesPage.builder()
                .items(List.of(expectedSite))
                .page(page)
                .size(size)
                .hasNext(true)
                .build();
        when(this.workspaceSiteMetaJpaRepository.findByUserIdAndStatus_IdNotAndDeletedAtIsNull(
                eq(userId),
                eq(3L),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(
                List.of(entity),
                PageRequest.of(page, size),
                41
        ));
        when(this.workspaceSiteMetaInfraMapper.asDomain(entity)).thenReturn(mapped);

        //when
        final WorkspaceSitesPage actual = this.workspaceSiteMetaRepository.findActiveByUserId(userId, page, size);

        //then
        assertThat(actual).isEqualTo(expected);

        final ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(this.workspaceSiteMetaJpaRepository).findByUserIdAndStatus_IdNotAndDeletedAtIsNull(
                eq(userId),
                eq(3L),
                pageableCaptor.capture()
        );
        final Pageable actualPageable = pageableCaptor.getValue();
        assertThat(actualPageable.getPageNumber()).isEqualTo(page);
        assertThat(actualPageable.getPageSize()).isEqualTo(size);
        assertThat(actualPageable.getSort().getOrderFor("updatedAt").isDescending()).isTrue();
        verify(this.workspaceSiteMetaInfraMapper).asDomain(entity);
    }
}
