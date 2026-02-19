package com.sitionix.wagssox.pipe.sitemeta.handler.registry;

import com.sitionix.wagssox.pipe.sitemeta.handler.SiteMetaPayloadHandler;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SiteMetaPayloadHandlerRegistry {

    private final Map<Class<?>, SiteMetaPayloadHandler<?>> handlers;

    public SiteMetaPayloadHandlerRegistry(final List<SiteMetaPayloadHandler<?>> handlers) {
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(
                        SiteMetaPayloadHandler::supports,
                        Function.identity(),
                        (left, right) -> {
                            throw new IllegalStateException("Duplicate site-meta handler for type: " + left.supports().getName());
                        }
                ));
    }

    public boolean handle(final Object payload) {
        if (payload == null) {
            return false;
        }

        final SiteMetaPayloadHandler<Object> handler = this.findHandler(payload.getClass());
        if (handler == null) {
            return false;
        }

        handler.handle(payload);
        return true;
    }

    @SuppressWarnings("unchecked")
    private SiteMetaPayloadHandler<Object> findHandler(final Class<?> type) {
        final SiteMetaPayloadHandler<Object> direct = (SiteMetaPayloadHandler<Object>) this.handlers.get(type);
        if (direct != null) {
            return direct;
        }

        return (SiteMetaPayloadHandler<Object>) this.handlers.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(type))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }
}
