package com.sitionix.wagssox.it.infra;

import com.sitionix.forgeit.domain.endpoint.Endpoint;
import com.sitionix.forgeit.domain.endpoint.HttpMethod;
import com.sitionix.forgeit.domain.endpoint.mockmvc.MockmvcDefault;
import org.springframework.http.HttpStatus;

public class ControllerEndpoint {

    public static final Endpoint<Object, Object> GET_SITES_FIRST_PAGE =
            Endpoint.createContract(
                    "/api/v1/sites?userId=123&page=0&size=20",
                    HttpMethod.GET,
                    Object.class,
                    Object.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
            );

    public static final Endpoint<Object, Object> GET_SITES_NEXT_PAGE =
            Endpoint.createContract(
                    "/api/v1/sites?userId=123&page=1&size=20",
                    HttpMethod.GET,
                    Object.class,
                    Object.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
            );

    public static final Endpoint<Object, Object> GET_SITES_MISSING_USER_ID =
            Endpoint.createContract(
                    "/api/v1/sites?page=0&size=20",
                    HttpMethod.GET,
                    Object.class,
                    Object.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
            );

    public static final Endpoint<Object, Object> GET_SITES_INVALID_SIZE =
            Endpoint.createContract(
                    "/api/v1/sites?userId=123&page=0&size=10",
                    HttpMethod.GET,
                    Object.class,
                    Object.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
            );

    public static final Endpoint<Object, Object> GET_SITES_NEGATIVE_PAGE =
            Endpoint.createContract(
                    "/api/v1/sites?userId=123&page=-1&size=20",
                    HttpMethod.GET,
                    Object.class,
                    Object.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
            );

    private ControllerEndpoint() {
    }
}
