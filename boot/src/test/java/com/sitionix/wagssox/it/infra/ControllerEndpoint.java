package com.sitionix.wagssox.it.infra;

import com.sitionix.forgeit.domain.endpoint.Endpoint;
import com.sitionix.forgeit.domain.endpoint.HttpMethod;
import com.sitionix.forgeit.domain.endpoint.mockmvc.MockmvcDefault;
import org.springframework.http.HttpStatus;

public class ControllerEndpoint {

    public static Endpoint<Object, Object> getSites() {
        return Endpoint.createContract(
                "/api/v1/sites",
                HttpMethod.GET,
                Object.class,
                Object.class,
                (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
        );
    }

    private ControllerEndpoint() {
    }
}
