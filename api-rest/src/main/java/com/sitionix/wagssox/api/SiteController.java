package com.sitionix.wagssox.api;

import com.app_afesox.wagssox.api_first.api.SiteApi;
import com.app_afesox.wagssox.api_first.dto.WorkspaceSitesPageDTO;
import com.sitionix.wagssox.api.mapper.SiteApiMapper;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.usecase.GetWorkspaceSites;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SiteController implements SiteApi {

    private final SiteApiMapper siteApiMapper;
    private final GetWorkspaceSites getWorkspaceSites;

    @Override
    public ResponseEntity<WorkspaceSitesPageDTO> getSites(
            final Integer size,
            final Integer page
    ) {
        final WorkspaceSitesPage response = this.getWorkspaceSites.execute(page, size);
        return ResponseEntity.ok(this.siteApiMapper.asWorkspaceSitesPageDTO(response));
    }
}
