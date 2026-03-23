package com.sitionix.wagssox.api;

import com.app_afesox.wagssox.api_first.api.SiteApi;
import com.app_afesox.wagssox.api_first.dto.SiteOverviewDTO;
import com.app_afesox.wagssox.api_first.dto.WorkspaceSitesPageDTO;
import com.sitionix.wagssox.domain.SiteOverview;
import com.sitionix.wagssox.api.mapper.SiteApiMapper;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.usecase.GetSiteOverview;
import com.sitionix.wagssox.domain.usecase.GetWorkspaceSites;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SiteController implements SiteApi {

    private final SiteApiMapper siteApiMapper;
    private final GetWorkspaceSites getWorkspaceSites;
    private final GetSiteOverview getSiteOverview;

    @Override
    public ResponseEntity<WorkspaceSitesPageDTO> getSites(
            final Integer page,
            final Integer size
    ) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        final WorkspaceSitesPage response = this.getWorkspaceSites.execute(pageable);
        return ResponseEntity.ok(this.siteApiMapper.asWorkspaceSitesPageDTO(response));
    }

    @Override
    public ResponseEntity<SiteOverviewDTO> getSiteOverview(final UUID siteId) {
        final SiteOverview response = this.getSiteOverview.execute(siteId);
        return ResponseEntity.ok(this.siteApiMapper.asSiteOverviewDTO(response));
    }
}
