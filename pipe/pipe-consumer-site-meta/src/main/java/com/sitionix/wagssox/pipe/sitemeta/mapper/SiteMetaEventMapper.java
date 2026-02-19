package com.sitionix.wagssox.pipe.sitemeta.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SiteMetaEventMapper {

    private final List<EventMapper<?, ?>> eventMappers;

    public <R> R asProjection(final Object payload, final Class<R> resultType) {
        final EventMapper<Object, R> mapper = this.resolve(payload.getClass(), resultType);
        return mapper.asProjection(payload);
    }

    @SuppressWarnings("unchecked")
    private <R> EventMapper<Object, R> resolve(final Class<?> payloadType, final Class<R> resultType) {
        for (final EventMapper<?, ?> eventMapper : this.eventMappers) {
            if (eventMapper.supports(payloadType, resultType)) {
                return (EventMapper<Object, R>) eventMapper;
            }
        }
        throw new IllegalArgumentException(
                "No mapper found for payload type " + payloadType.getName() + " and result type " + resultType.getName()
        );
    }
}
