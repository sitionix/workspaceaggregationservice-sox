package com.sitionix.wagssox.domain.event.payload;

import com.sitionix.forge.inbox.core.model.InboxAggregateType;
import com.sitionix.forge.inbox.core.port.ForgeInboxPayload;
import com.sitionix.wagssox.domain.SiteMetaDelete;
import com.sitionix.wagssox.domain.SiteMetaUpdate;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;

/**
 * Inbox payload marker for site metadata projection events.
 */
public interface SiteMetaInboxPayload extends ForgeInboxPayload {

    @Override
    default InboxAggregateType aggregateType() {
        return InboxAggregateType.USER;
    }

    @Override
    default String traceId() {
        return null;
    }

    static Long resolveUserAggregateId(final WorkspaceSiteMeta siteMeta) {
        if (siteMeta == null) {
            return null;
        }
        return siteMeta.getUserId();
    }

    static Long resolveUserAggregateId(final SiteMetaUpdate siteMetaUpdate) {
        if (siteMetaUpdate == null) {
            return null;
        }
        return siteMetaUpdate.getUserId();
    }

    static Long resolveUserAggregateId(final SiteMetaDelete siteMetaDelete) {
        if (siteMetaDelete == null) {
            return null;
        }
        return siteMetaDelete.getUserId();
    }
}
