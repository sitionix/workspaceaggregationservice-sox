package com.sitionix.wagssox.pipe.sitemeta.mapper;

public interface EventMapper<P, R> {

    Class<P> payloadType();

    Class<R> resultType();

    R asProjection(P payload);

    default boolean supports(final Class<?> payloadType, final Class<?> resultType) {
        return this.payloadType().isAssignableFrom(payloadType) && this.resultType().equals(resultType);
    }
}
