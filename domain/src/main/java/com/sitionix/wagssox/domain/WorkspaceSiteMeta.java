package com.sitionix.wagssox.domain;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSiteMeta {

    private UUID siteId;
    private Long userId;
    private String name;
    private WorkspaceSiteMetaStatus status;
    private WorkspaceSiteMetaType type;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
