package com.sitionix.wagssox.infrastructure.postgresql.repository;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import com.sitionix.wagssox.infrastructure.postgresql.jpa.WorkspaceSiteMetaJpaRepository;
import com.sitionix.wagssox.infrastructure.postgresql.mapper.WorkspaceSiteMetaInfraMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
    public List<WorkspaceSiteMeta> findByOwnerUserId(final Long ownerUserId) {
        return this.workspaceSiteMetaJpaRepository.findAllByOwnerUserIdOrderByUpdatedAtDesc(ownerUserId)
                .stream()
                .map(this.workspaceSiteMetaInfraMapper::asDomain)
                .toList();
    }

    @Override
    public void deleteBySiteId(final UUID siteId) {
        this.workspaceSiteMetaJpaRepository.deleteById(siteId);
    }
}
