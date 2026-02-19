package com.sitionix.wagssox.api.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSitesResponseDTO {

    private List<WorkspaceSiteCardResponseDTO> items;
    private Integer page;
    private Integer size;
    private Boolean hasNext;
}
