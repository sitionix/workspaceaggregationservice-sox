package com.sitionix.wagssox.it;

import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import com.sitionix.wagssox.it.infra.DatabaseContract;
import com.sitionix.wagssox.it.kafka.SiteMetaKafkaContracts;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class SiteMetaConsumerIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("given site created event when consumed then projection saved in postgresql")
    void givenSiteCreatedEvent_whenConsumed_thenProjectionSavedInPostgresql() {
        //given
        final UUID expectedSiteId = UUID.fromString("55db7314-63a5-49b5-bdb6-6a6cc59e61b9");
        final Instant expectedCreatedAt = Instant.parse("2026-02-18T10:00:00Z");
        final Instant expectedUpdatedAt = Instant.parse("2026-02-18T10:00:00Z");

        //when
        this.testManager.kafka()
                .publish(SiteMetaKafkaContracts.SITE_META_CREATED_INPUT)
                .sendAndVerify(result -> this.testManager.postgresql()
                        .get(WorkspaceSiteMetaEntity.class)
                        .singleElement()
                        .andExpected(entity -> Objects.equals(entity.getSiteId(), expectedSiteId))
                        .andExpected(entity -> Objects.equals(entity.getUserId(), 17L))
                        .andExpected(entity -> Objects.equals(entity.getName(), "Site A"))
                        .andExpected(entity -> Objects.equals(entity.getStatus().getId(), 1L))
                        .andExpected(entity -> Objects.equals(entity.getType().getId(), 3L))
                        .andExpected(entity -> Objects.equals(entity.getDescription(), "Description"))
                        .andExpected(entity -> Objects.equals(entity.getCreatedAt(), expectedCreatedAt))
                        .andExpected(entity -> Objects.equals(entity.getUpdatedAt(), expectedUpdatedAt))
                        .andExpected(entity -> Objects.isNull(entity.getDeletedAt()))
                        .assertEntity());
    }

    @Test
    @DisplayName("given existing site meta when site updated event consumed then projection updated in postgresql")
    void givenExistingSiteMeta_whenSiteUpdatedEventConsumed_thenProjectionUpdatedInPostgresql() {
        //given
        final UUID expectedSiteId = UUID.fromString("a4daef31-b03a-4e90-9fe9-297c74eaf628");
        final Instant expectedCreatedAt = Instant.parse("2026-02-18T11:00:00Z");
        final Instant expectedUpdatedAt = Instant.parse("2026-02-18T12:00:00Z");

        this.testManager.postgresql()
                .create()
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_TYPE_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withJson("workspaceSiteMetaBeforeUpdate.json"))
                .build();

        //when
        this.testManager.kafka()
                .publish(SiteMetaKafkaContracts.SITE_META_UPDATED_INPUT)
                .sendAndVerify(result -> this.testManager.postgresql()
                        .get(WorkspaceSiteMetaEntity.class)
                        .singleElement()
                        .andExpected(entity -> Objects.equals(entity.getSiteId(), expectedSiteId))
                        .andExpected(entity -> Objects.equals(entity.getUserId(), 21L))
                        .andExpected(entity -> Objects.equals(entity.getName(), "Updated Site Name"))
                        .andExpected(entity -> Objects.equals(entity.getStatus().getId(), 2L))
                        .andExpected(entity -> Objects.equals(entity.getType().getId(), 2L))
                        .andExpected(entity -> Objects.equals(entity.getDescription(), "Updated description"))
                        .andExpected(entity -> Objects.equals(entity.getCreatedAt(), expectedCreatedAt))
                        .andExpected(entity -> Objects.equals(entity.getUpdatedAt(), expectedUpdatedAt))
                        .andExpected(entity -> Objects.isNull(entity.getDeletedAt()))
                        .assertEntity());
    }

    @Test
    @DisplayName("given site deleted event when consumed then projection soft deleted in postgresql")
    void givenSiteDeletedEvent_whenConsumed_thenProjectionSoftDeletedInPostgresql() {
        //given
        final UUID expectedSiteId = UUID.fromString("be8dbc7c-5d3f-4195-b6f5-80e36195fe5a");
        final Instant expectedDeletedAt = Instant.parse("2026-02-18T12:10:00Z");

        this.testManager.postgresql()
                .create()
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_TYPE_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withJson("workspaceSiteMetaBeforeDelete.json"))
                .build();

        //when
        this.testManager.kafka()
                .publish(SiteMetaKafkaContracts.SITE_META_DELETED_INPUT)
                .sendAndVerify(result -> this.testManager.postgresql()
                        .get(WorkspaceSiteMetaEntity.class)
                        .singleElement()
                        .andExpected(entity -> Objects.equals(entity.getSiteId(), expectedSiteId))
                        .andExpected(entity -> Objects.equals(entity.getDeletedAt(), expectedDeletedAt))
                        .andExpected(entity -> Objects.equals(entity.getUpdatedAt(), expectedDeletedAt))
                        .assertEntity());
    }
}
