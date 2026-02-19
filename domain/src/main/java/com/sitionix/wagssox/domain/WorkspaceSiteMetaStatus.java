package com.sitionix.wagssox.domain;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum WorkspaceSiteMetaStatus {
    DRAFT(1L),
    PUBLISHED(2L),
    ARCHIVED(3L);

    private final Long id;

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
