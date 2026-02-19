package com.sitionix.wagssox.it;

import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.domain.contract.graph.DbGraphChain;
import com.sitionix.forgeit.mockmvc.api.QueryParams;
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
    @DisplayName("given active archived and deleted sites when get first page then return only active sorted by updatedAt desc")
    void givenActiveArchivedAndDeletedSites_whenGetFirstPage_thenReturnOnlyActiveSortedByUpdatedAtDesc() {
        //given
        final Instant baseUpdatedAt = Instant.parse("2026-01-01T00:00:00Z");
        DbGraphChain<?> dbGraph = this.testManager.postgresql()
                .create()
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_TYPE_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                        new WorkspaceSiteMetaEntity(
                                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                                123L,
                                "Draft site 1",
                                null,
                                null,
                                "Description 1",
                                baseUpdatedAt,
                                baseUpdatedAt.plusSeconds(10),
                                null
                        )
                ))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                        new WorkspaceSiteMetaEntity(
                                UUID.fromString("00000000-0000-0000-0000-000000000002"),
                                123L,
                                "Draft site 2",
                                null,
                                null,
                                "Description 2",
                                baseUpdatedAt,
                                baseUpdatedAt.plusSeconds(5),
                                null
                        )
                ))
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(2L))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                        new WorkspaceSiteMetaEntity(
                                UUID.fromString("00000000-0000-0000-0000-000000000003"),
                                123L,
                                null,
                                null,
                                null,
                                "Description 3",
                                baseUpdatedAt,
                                baseUpdatedAt.plusSeconds(20),
                                null
                        )
                ));

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
                .ping(ControllerEndpoint.getSites())
                .header("X-Forge-User-Sub", "123")
                .withQueryParameters(QueryParams.create()
                        .add("page", 0)
                        .add("size", 20))
                .expectResponse("getSitesFirstPageResponse.json")
                .assertDefault();
    }

    @Test
    @DisplayName("given less than one page of active sites when get next page then return empty items and hasNext false")
    void givenLessThanOnePageOfActiveSites_whenGetNextPage_thenReturnEmptyItemsAndHasNextFalse() {
        //given
        final Instant baseUpdatedAt = Instant.parse("2026-01-01T00:00:00Z");
        this.testManager.postgresql()
                .create()
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_TYPE_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                        new WorkspaceSiteMetaEntity(
                                UUID.fromString("00000000-0000-0000-0000-000000000010"),
                                123L,
                                "Site 10",
                                null,
                                null,
                                "Description 10",
                                baseUpdatedAt,
                                baseUpdatedAt.plusSeconds(10),
                                null
                        )
                ))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                        new WorkspaceSiteMetaEntity(
                                UUID.fromString("00000000-0000-0000-0000-000000000011"),
                                123L,
                                "Site 11",
                                null,
                                null,
                                "Description 11",
                                baseUpdatedAt,
                                baseUpdatedAt.plusSeconds(11),
                                null
                        )
                ))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withEntity(
                        new WorkspaceSiteMetaEntity(
                                UUID.fromString("00000000-0000-0000-0000-000000000012"),
                                123L,
                                "Site 12",
                                null,
                                null,
                                "Description 12",
                                baseUpdatedAt,
                                baseUpdatedAt.plusSeconds(12),
                                null
                        )
                ))
                .build();

        //when then
        this.testManager.mockMvc()
                .ping(ControllerEndpoint.getSites())
                .header("X-Forge-User-Sub", "123")
                .withQueryParameters(QueryParams.create()
                        .add("page", 1)
                        .add("size", 20))
                .expectResponse("getSitesSecondPageResponse.json")
                .assertDefault();
    }

    @Test
    @DisplayName("given missing user context when get sites then return unauthorized")
    void givenMissingUserContext_whenGetSites_thenReturnUnauthorized() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(ControllerEndpoint.getSites())
                .header("X-Forge-User-Sub", null)
                .withQueryParameters(QueryParams.create()
                        .add("page", 0)
                        .add("size", 20))
                .expectStatus(HttpStatus.UNAUTHORIZED)
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.UNAUTHORIZED.value()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.title").value(HttpStatus.UNAUTHORIZED.getReasonPhrase()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.details").isNotEmpty())
                .assertDefault();
    }

    @Test
    @DisplayName("given invalid size when get sites then return bad request with validation error")
    void givenInvalidSize_whenGetSites_thenReturnBadRequestWithValidationError() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(ControllerEndpoint.getSites())
                .header("X-Forge-User-Sub", "123")
                .withQueryParameters(QueryParams.create()
                        .add("page", 0)
                        .add("size", 10))
                .expectStatus(HttpStatus.BAD_REQUEST)
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.title").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.details").isNotEmpty())
                .assertDefault();
    }

    @Test
    @DisplayName("given missing size when get sites then return bad request with validation error")
    void givenMissingSize_whenGetSites_thenReturnBadRequestWithValidationError() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(ControllerEndpoint.getSites())
                .header("X-Forge-User-Sub", "123")
                .withQueryParameters(QueryParams.create()
                        .add("page", 0))
                .expectStatus(HttpStatus.BAD_REQUEST)
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.title").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.details").isNotEmpty())
                .assertDefault();
    }

    @Test
    @DisplayName("given negative page when get sites then return bad request with validation error")
    void givenNegativePage_whenGetSites_thenReturnBadRequestWithValidationError() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(ControllerEndpoint.getSites())
                .header("X-Forge-User-Sub", "123")
                .withQueryParameters(QueryParams.create()
                        .add("page", -1)
                        .add("size", 20))
                .expectStatus(HttpStatus.BAD_REQUEST)
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.title").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpectPath(MockMvcResultMatchers.jsonPath("$.details").isNotEmpty())
                .assertDefault();
    }
}
