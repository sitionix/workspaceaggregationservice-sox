package com.sitionix.wagssox.it;

import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.domain.contract.graph.DbGraphChain;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaEntity;
import com.sitionix.wagssox.it.infra.ControllerEndpoint;
import com.sitionix.wagssox.it.infra.DatabaseContract;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTest
class SiteControllerIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("given active archived and deleted sites when get first page then return only twenty active sorted by updatedAt desc")
    void givenActiveArchivedAndDeletedSites_whenGetFirstPage_thenReturnOnlyTwentyActiveSortedByUpdatedAtDesc() {
        //given
        final Instant baseUpdatedAt = Instant.parse("2026-01-01T00:00:00Z");
        DbGraphChain<?> dbGraph = this.testManager.postgresql()
                .create()
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_TYPE_ENTITY_DB_CONTRACT.getById(1L));

        for (int index = 1; index <= 20; index++) {
            dbGraph = dbGraph.to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                    new WorkspaceSiteMetaEntity(
                            UUID.fromString(String.format("00000000-0000-0000-0000-%012d", index)),
                            123L,
                            "Draft site " + index,
                            null,
                            null,
                            "Description " + index,
                            baseUpdatedAt,
                            baseUpdatedAt.plusSeconds(index),
                            null
                    )
            ));
        }

        dbGraph = dbGraph.to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(2L));
        for (int index = 21; index <= 25; index++) {
            dbGraph = dbGraph.to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                    new WorkspaceSiteMetaEntity(
                            UUID.fromString(String.format("00000000-0000-0000-0000-%012d", index)),
                            123L,
                            index == 25 ? null : "Published site " + index,
                            null,
                            null,
                            "Description " + index,
                            baseUpdatedAt,
                            baseUpdatedAt.plusSeconds(index),
                            null
                    )
            ));
        }

        dbGraph = dbGraph
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(3L))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                        new WorkspaceSiteMetaEntity(
                                UUID.fromString("00000000-0000-0000-0000-000000000900"),
                                123L,
                                "Archived site",
                                null,
                                null,
                                "Archived description",
                                baseUpdatedAt,
                                baseUpdatedAt.plusSeconds(900),
                                null
                        )
                ))
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                        new WorkspaceSiteMetaEntity(
                                UUID.fromString("00000000-0000-0000-0000-000000000901"),
                                123L,
                                "Deleted site",
                                null,
                                null,
                                "Deleted description",
                                baseUpdatedAt,
                                baseUpdatedAt.plusSeconds(901),
                                baseUpdatedAt.plusSeconds(905)
                        )
                ))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                        new WorkspaceSiteMetaEntity(
                                UUID.fromString("00000000-0000-0000-0000-000000000902"),
                                999L,
                                "Other user site",
                                null,
                                null,
                                "Other user description",
                                baseUpdatedAt,
                                baseUpdatedAt.plusSeconds(902),
                                null
                        )
                ));

        dbGraph.build();

        //when then
        this.testManager.mockMvc()
                .ping(ControllerEndpoint.GET_SITES_FIRST_PAGE)
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items.length()").value(20))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.page").value(0))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.size").value(20))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.hasNext").value(true))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[0].siteId")
                        .value("00000000-0000-0000-0000-000000000025"))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[0].name")
                        .value("Untitled site"))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[0].status")
                        .value("PUBLISHED"))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[19].siteId")
                        .value("00000000-0000-0000-0000-000000000006"))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[?(@.siteId=='00000000-0000-0000-0000-000000000900')]")
                        .isEmpty())
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[?(@.siteId=='00000000-0000-0000-0000-000000000901')]")
                        .isEmpty())
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[?(@.siteId=='00000000-0000-0000-0000-000000000902')]")
                        .isEmpty())
                .assertDefault();
    }

    @Test
    @DisplayName("given twenty five active sites when get next page then return last five without overlap and hasNext false")
    void givenTwentyFiveActiveSites_whenGetNextPage_thenReturnLastFiveWithoutOverlapAndHasNextFalse() {
        //given
        final Instant baseUpdatedAt = Instant.parse("2026-01-01T00:00:00Z");
        DbGraphChain<?> dbGraph = this.testManager.postgresql()
                .create()
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_TYPE_ENTITY_DB_CONTRACT.getById(1L));

        for (int index = 1; index <= 25; index++) {
            dbGraph = dbGraph.to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                    new WorkspaceSiteMetaEntity(
                            UUID.fromString(String.format("00000000-0000-0000-0000-%012d", index)),
                            123L,
                            "Site " + index,
                            null,
                            null,
                            "Description " + index,
                            baseUpdatedAt,
                            baseUpdatedAt.plusSeconds(index),
                            null
                    )
            ));
        }

        dbGraph.build();

        //when then
        this.testManager.mockMvc()
                .ping(ControllerEndpoint.GET_SITES_NEXT_PAGE)
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items.length()").value(5))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.page").value(1))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.size").value(20))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.hasNext").value(false))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[0].siteId")
                        .value("00000000-0000-0000-0000-000000000005"))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[1].siteId")
                        .value("00000000-0000-0000-0000-000000000004"))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[2].siteId")
                        .value("00000000-0000-0000-0000-000000000003"))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[3].siteId")
                        .value("00000000-0000-0000-0000-000000000002"))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.items[4].siteId")
                        .value("00000000-0000-0000-0000-000000000001"))
                .assertDefault();
    }

    @Test
    @DisplayName("given missing userId when get sites then return bad request with validation error")
    void givenMissingUserId_whenGetSites_thenReturnBadRequestWithValidationError() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(ControllerEndpoint.GET_SITES_MISSING_USER_ID)
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.title").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.details").value("userId is required"))
                .assertDefault();
    }

    @Test
    @DisplayName("given invalid size when get sites then return bad request with validation error")
    void givenInvalidSize_whenGetSites_thenReturnBadRequestWithValidationError() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(ControllerEndpoint.GET_SITES_INVALID_SIZE)
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.title").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.details").value("size must be 20"))
                .assertDefault();
    }

    @Test
    @DisplayName("given negative page when get sites then return bad request with validation error")
    void givenNegativePage_whenGetSites_thenReturnBadRequestWithValidationError() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(ControllerEndpoint.GET_SITES_NEGATIVE_PAGE)
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.title").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.details").value("page must be >= 0"))
                .assertDefault();
    }
}
