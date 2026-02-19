package com.sitionix.wagssox.it.infra;

import com.app_afesox.wagssox.api_first.dto.WorkspaceSitesPageDTO;
import com.sitionix.forgeit.domain.endpoint.Endpoint;
import com.sitionix.forgeit.domain.endpoint.HttpMethod;
import com.sitionix.forgeit.domain.endpoint.mockmvc.MockmvcDefault;
import org.springframework.http.HttpStatus;

public class ControllerEndpoint {

    public static Endpoint<Void, WorkspaceSitesPageDTO> getSites() {
        return Endpoint.createContract(
                "/api/v1/sites",
                HttpMethod.GET,
                Void.class,
                WorkspaceSitesPageDTO.class,
                (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
        );
    }

    private ControllerEndpoint() {
    }
}
