package com.sitionix.wagssox.it.kafka;

import com.app_afesox.events.Metadata;
import com.app_afesox.stsssox.events.kafka.AvroRecordSerializer;
import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteMetaEnvelope;
import com.sitionix.forgeit.kafka.api.KafkaContract;

public final class SiteMetaKafkaContracts {

    public static final KafkaContract<SiteMetaEnvelope> SITE_META_INPUT =
            KafkaContract.producerContract()
                    .defaultEnvelope(SiteMetaEnvelope.class)
                    .topic("stsssox.it.site-meta.public.unified.v1")
                    .defaultPayload(SiteCreatedEvent.class, "defaultSiteCreatedEvent.json")
                    .defaultMetadata(Metadata.class, "defaultSiteMetaMetadata.json")
                    .payloadSerializer(AvroRecordSerializer.class)
                    .build();

    private SiteMetaKafkaContracts() {
    }
}
