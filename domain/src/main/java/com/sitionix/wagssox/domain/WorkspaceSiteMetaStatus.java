package com.sitionix.wagssox.domain;

import java.util.Arrays;

public enum WorkspaceSiteMetaStatus {
    DRAFT(1L),
    PUBLISHED(2L),
    ARCHIVED(3L);

    private final Long id;

    WorkspaceSiteMetaStatus(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public static WorkspaceSiteMetaStatus fromId(final Long id) {
        if (id == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(status -> status.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown WorkspaceSiteMetaStatus id: " + id));
    }
}
