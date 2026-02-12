package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.WorkspaceAggregation;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceAggregationQuery {
    Optional<WorkspaceAggregation> findById(UUID id);
}
