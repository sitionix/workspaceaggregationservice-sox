package com.sitionix.wagssox.api.dto;

import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSiteCardResponseDTO {

    private UUID siteId;
    private String name;
    private WorkspaceSiteMetaStatus status;
    private WorkspaceSiteMetaType type;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
