package com.sitionix.wagssox.it;

import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.mockmvc.api.QueryParams;
import com.sitionix.wagssox.it.infra.ControllerEndpoint;
import com.sitionix.wagssox.it.infra.DatabaseContract;
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
        this.testManager.postgresql()
                .create()
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(2L))
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(3L))
                .to(DatabaseContract.WORKSPACE_SITE_META_TYPE_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withJson("workspaceSiteMetaGetSitesDraft1.json"))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withJson("workspaceSiteMetaGetSitesDraft2.json"))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withJson("workspaceSiteMetaGetSitesPublishedUntitled.json"))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withJson("workspaceSiteMetaGetSitesArchived.json"))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withJson("workspaceSiteMetaGetSitesDeleted.json"))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withJson("workspaceSiteMetaGetSitesOtherUser.json"))
                .build();

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
        this.testManager.postgresql()
                .create()
                .to(DatabaseContract.WORKSPACE_SITE_META_STATUS_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_TYPE_ENTITY_DB_CONTRACT.getById(1L))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withJson("workspaceSiteMetaGetSitesPage2A.json"))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withJson("workspaceSiteMetaGetSitesPage2B.json"))
                .to(DatabaseContract.WORKSPACE_SITE_META_ENTITY_DB_CONTRACT.withJson("workspaceSiteMetaGetSitesPage2C.json"))
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
