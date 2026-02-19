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
public class SiteMetaDelete {

    private UUID siteId;
    private Long userId;
    private Instant deletedAt;
}
