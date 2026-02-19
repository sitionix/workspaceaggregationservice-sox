package com.sitionix.wagssox.pipe.sitemeta.handler;

public interface SiteMetaPayloadHandler<P> {

    Class<P> supports();

    void handle(P payload);
}
