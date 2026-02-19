package com.sitionix.wagssox.api;

import com.sitionix.wagssox.api.dto.WorkspaceSitesResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface SiteApi {

    @GetMapping("/api/v1/sites")
    ResponseEntity<WorkspaceSitesResponseDTO> getSites(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer size
    );
}
