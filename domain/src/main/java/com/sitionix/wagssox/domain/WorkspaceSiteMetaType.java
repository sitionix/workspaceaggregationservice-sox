package com.sitionix.wagssox.domain;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum WorkspaceSiteMetaType {
    PORTFOLIO(1L),
    BUSINESS(2L),
    BLOG(3L),
    STORE(4L),
    LANDING(5L),
    OTHER(6L);

    private final Long id;

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
