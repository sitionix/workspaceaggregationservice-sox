package com.sitionix.wagssox.domain;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class WorkspaceSiteMetaSlice {

    private List<WorkspaceSiteMeta> items;
    private Boolean hasNext;
}
