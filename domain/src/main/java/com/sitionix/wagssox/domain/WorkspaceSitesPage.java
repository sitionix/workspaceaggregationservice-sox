package com.sitionix.wagssox.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSitesPage {

    private List<WorkspaceSiteMeta> items;
    private Integer page;
    private Integer size;
    private Boolean hasNext;
}
