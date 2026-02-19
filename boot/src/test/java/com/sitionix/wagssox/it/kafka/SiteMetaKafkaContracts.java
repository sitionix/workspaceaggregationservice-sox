package com.sitionix.wagssox.it.kafka;

import com.app_afesox.events.Metadata;
import com.app_afesox.stsssox.events.kafka.AvroRecordSerializer;
import com.app_afesox.stsssox.events.sitemeta.SiteCreatedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteDeletedEvent;
import com.app_afesox.stsssox.events.sitemeta.SiteMetaEnvelope;
import com.app_afesox.stsssox.events.sitemeta.SiteUpdatedEvent;
import com.sitionix.forgeit.kafka.api.KafkaContract;

public final class SiteMetaKafkaContracts {

    public static final KafkaContract<SiteMetaEnvelope> SITE_META_CREATED_INPUT =
            KafkaContract.producerContract()
                    .defaultEnvelope(SiteMetaEnvelope.class)
                    .topic("stsssox.it.site-meta.public.unified.v1")
                    .defaultPayload(SiteCreatedEvent.class, "defaultSiteCreatedEvent.json")
                    .defaultMetadata(Metadata.class, "defaultSiteMetaMetadata.json")
                    .payloadSerializer(AvroRecordSerializer.class)
                    .build();

    public static final KafkaContract<SiteMetaEnvelope> SITE_META_UPDATED_INPUT =
            KafkaContract.producerContract()
                    .defaultEnvelope(SiteMetaEnvelope.class)
                    .topic("stsssox.it.site-meta.public.unified.v1")
                    .defaultPayload(SiteUpdatedEvent.class, "defaultSiteUpdatedEvent.json")
                    .defaultMetadata(Metadata.class, "defaultSiteMetaMetadata.json")
                    .payloadSerializer(AvroRecordSerializer.class)
                    .build();

    public static final KafkaContract<SiteMetaEnvelope> SITE_META_DELETED_INPUT =
            KafkaContract.producerContract()
                    .defaultEnvelope(SiteMetaEnvelope.class)
                    .topic("stsssox.it.site-meta.public.unified.v1")
                    .defaultPayload(SiteDeletedEvent.class, "defaultSiteDeletedEvent.json")
                    .defaultMetadata(Metadata.class, "defaultSiteMetaMetadata.json")
                    .payloadSerializer(AvroRecordSerializer.class)
                    .build();

    private SiteMetaKafkaContracts() {
    }
}
