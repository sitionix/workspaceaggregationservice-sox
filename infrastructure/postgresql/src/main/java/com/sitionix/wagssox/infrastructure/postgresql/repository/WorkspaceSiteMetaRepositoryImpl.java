package com.sitionix.wagssox.infrastructure.postgresql.repository;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import com.sitionix.wagssox.infrastructure.postgresql.jpa.WorkspaceSiteMetaJpaRepository;
import com.sitionix.wagssox.infrastructure.postgresql.mapper.WorkspaceSiteMetaInfraMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WorkspaceSiteMetaRepositoryImpl implements WorkspaceSiteMetaRepository {

    private final WorkspaceSiteMetaJpaRepository workspaceSiteMetaJpaRepository;
    private final WorkspaceSiteMetaInfraMapper workspaceSiteMetaInfraMapper;

    @Override
    public WorkspaceSiteMeta save(final WorkspaceSiteMeta siteMeta) {
        final WorkspaceSiteMetaEntity savedEntity = this.workspaceSiteMetaJpaRepository
                .save(this.workspaceSiteMetaInfraMapper.asEntity(siteMeta));
        return this.workspaceSiteMetaInfraMapper.asDomain(savedEntity);
    }

    @Override
    public Optional<WorkspaceSiteMeta> findBySiteId(final UUID siteId) {
        return this.workspaceSiteMetaJpaRepository.findById(siteId)
                .map(this.workspaceSiteMetaInfraMapper::asDomain);
    }

    @Override
    public WorkspaceSitesPage findActiveByUserId(final Long userId, final Integer page, final Integer size) {
        final Page<WorkspaceSiteMetaEntity> entities = this.workspaceSiteMetaJpaRepository.findActiveByUserId(
                userId,
                WorkspaceSiteMetaStatus.ARCHIVED.getId(),
                PageRequest.of(page, size)
        );
        return this.workspaceSiteMetaInfraMapper.asWorkspaceSitesPage(entities);
    }
}
