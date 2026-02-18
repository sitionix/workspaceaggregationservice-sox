package com.sitionix.wagssox.it;

import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
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
                .publish(SiteMetaKafkaContracts.SITE_META_INPUT)
                .sendAndVerify(result -> this.testManager.postgresql()
                        .get(WorkspaceSiteMetaEntity.class)
                        .singleElement()
                        .andExpected(entity -> Objects.equals(entity.getSiteId(), expectedSiteId))
                        .andExpected(entity -> Objects.equals(entity.getOwnerUserId(), 17L))
                        .andExpected(entity -> Objects.equals(entity.getName(), "Site A"))
                        .andExpected(entity -> Objects.equals(entity.getStatus(), WorkspaceSiteMetaStatus.DRAFT))
                        .andExpected(entity -> Objects.equals(entity.getType(), WorkspaceSiteMetaType.BLOG))
                        .andExpected(entity -> Objects.equals(entity.getDescription(), "Description"))
                        .andExpected(entity -> Objects.equals(entity.getCreatedAt(), expectedCreatedAt))
                        .andExpected(entity -> Objects.equals(entity.getUpdatedAt(), expectedUpdatedAt))
                        .assertEntity());
    }
}
