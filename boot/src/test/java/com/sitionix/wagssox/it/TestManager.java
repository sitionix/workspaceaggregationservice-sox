package com.sitionix.wagssox.it;

import com.sitionix.forgeit.core.annotation.ForgeFeatures;
import com.sitionix.forgeit.core.api.ForgeIT;
import com.sitionix.forgeit.kafka.api.KafkaSupport;
import com.sitionix.forgeit.postgresql.api.PostgresqlSupport;

@ForgeFeatures({KafkaSupport.class, PostgresqlSupport.class})
public interface TestManager extends ForgeIT {
}
