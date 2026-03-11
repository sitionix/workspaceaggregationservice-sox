package com.sitionix.wagssox.config;

import com.sitionix.forge.inbox.core.model.EnumForgeInboxEventTypes;
import com.sitionix.forge.inbox.core.model.ForgeInboxEventTypes;
import com.sitionix.wagssox.domain.event.SiteMetaEventType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InboxEventTypeConfiguration {

    @Bean
    public ForgeInboxEventTypes forgeInboxEventTypes() {
        return new EnumForgeInboxEventTypes<>(SiteMetaEventType.class);
    }
}
