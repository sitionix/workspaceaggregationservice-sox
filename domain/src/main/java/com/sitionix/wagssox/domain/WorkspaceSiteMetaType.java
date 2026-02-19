package com.sitionix.wagssox.domain;

import java.util.Arrays;

public enum WorkspaceSiteMetaType {
    PORTFOLIO(1L),
    BUSINESS(2L),
    BLOG(3L),
    STORE(4L),
    LANDING(5L),
    OTHER(6L);

    private final Long id;

    WorkspaceSiteMetaType(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public static WorkspaceSiteMetaType fromId(final Long id) {
        if (id == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(type -> type.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown WorkspaceSiteMetaType id: " + id));
    }
}
