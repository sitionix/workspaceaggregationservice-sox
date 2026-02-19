package com.sitionix.wagssox.it.infra;

import com.sitionix.forgeit.core.contract.ForgeDbContracts;
import com.sitionix.forgeit.domain.contract.DbContract;
import com.sitionix.forgeit.domain.contract.DbContractsDsl;
import com.sitionix.forgeit.domain.contract.clean.CleanupPolicy;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaStatusEntity;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaTypeEntity;

@ForgeDbContracts
public class DatabaseContract {

    public static final DbContract<WorkspaceSiteMetaStatusEntity> WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT =
            DbContractsDsl.entity(WorkspaceSiteMetaStatusEntity.class)
                    .cleanupPolicy(CleanupPolicy.NONE)
                    .build();

    public static final DbContract<WorkspaceSiteMetaTypeEntity> WORKSPACE_SITE_META_TYPE_ENTITY_DB_CONTRACT =
            DbContractsDsl.entity(WorkspaceSiteMetaTypeEntity.class)
                    .cleanupPolicy(CleanupPolicy.NONE)
                    .build();

    public static final DbContract<WorkspaceSiteMetaEntity> WORKSPACE_SITE_META_ENTITY_DB_CONTRACT =
            DbContractsDsl.entity(WorkspaceSiteMetaEntity.class)
                    .dependsOn(WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT, WorkspaceSiteMetaEntity::setStatus)
                    .dependsOn(WORKSPACE_SITE_META_TYPE_ENTITY_DB_CONTRACT, WorkspaceSiteMetaEntity::setType)
                    .cleanupPolicy(CleanupPolicy.DELETE_ALL)
                    .build();

    private DatabaseContract() {
    }
}
