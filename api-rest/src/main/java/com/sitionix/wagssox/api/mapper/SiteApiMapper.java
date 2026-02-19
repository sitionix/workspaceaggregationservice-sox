package com.sitionix.wagssox.api.mapper;

import com.sitionix.wagssox.api.dto.WorkspaceSiteCardResponseDTO;
import com.sitionix.wagssox.api.dto.WorkspaceSitesResponseDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SiteApiMapper {

    public WorkspaceSitesResponseDTO asWorkspaceSitesResponseDTO(final WorkspaceSitesPage src) {
        return WorkspaceSitesResponseDTO.builder()
                .items(this.asWorkspaceSiteCardResponseDTOs(src.getItems()))
                .page(src.getPage())
                .size(src.getSize())
                .hasNext(src.getHasNext())
                .build();
    }

    private List<WorkspaceSiteCardResponseDTO> asWorkspaceSiteCardResponseDTOs(final List<WorkspaceSiteMeta> src) {
        return src.stream()
                .map(this::asWorkspaceSiteCardResponseDTO)
                .toList();
    }

    private WorkspaceSiteCardResponseDTO asWorkspaceSiteCardResponseDTO(final WorkspaceSiteMeta src) {
        return WorkspaceSiteCardResponseDTO.builder()
                .siteId(src.getSiteId())
                .name(src.getName())
                .status(src.getStatus())
                .type(src.getType())
                .description(src.getDescription())
                .createdAt(src.getCreatedAt())
                .updatedAt(src.getUpdatedAt())
                .build();
    }
}
