package com.sitionix.wagssox.pipe.sitemeta;

import com.app_afesox.stsssox.events.sitemeta.SiteMetaEnvelope;
import com.app_afesox.stsssox.events.sitemeta.kafka.SitemetaV1ConsumerHandler;
import com.sitionix.wagssox.pipe.sitemeta.handler.registry.SiteMetaPayloadHandlerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiteMetaConsumer implements SitemetaV1ConsumerHandler {

    private final SiteMetaPayloadHandlerRegistry siteMetaPayloadHandlerRegistry;

    @Override
    public void consumeSiteMeta(final SiteMetaEnvelope siteMetaEnvelope) {
        if (isNull(siteMetaEnvelope) || isNull(siteMetaEnvelope.getPayload())) {
            return;
        }

        final Object payload = siteMetaEnvelope.getPayload();
        final boolean handled = this.siteMetaPayloadHandlerRegistry.handle(payload);
        if (!handled) {
            log.warn("Skip unsupported site-meta payload type: {}", payload.getClass().getName());
        }
    }
}
