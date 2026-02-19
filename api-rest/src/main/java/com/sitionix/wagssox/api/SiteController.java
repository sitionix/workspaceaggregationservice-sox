package com.sitionix.wagssox.api;

import com.sitionix.wagssox.api.dto.WorkspaceSitesResponseDTO;
import com.sitionix.wagssox.api.mapper.SiteApiMapper;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.usecase.GetWorkspaceSites;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SiteController implements SiteApi {

    private final SiteApiMapper siteApiMapper;
    private final GetWorkspaceSites getWorkspaceSites;

    @Override
    public ResponseEntity<WorkspaceSitesResponseDTO> getSites(
            final Long userId,
            final Integer page,
            final Integer size
    ) {
        this.validateRequest(userId, page, size);

        final WorkspaceSitesPage response = this.getWorkspaceSites.execute(userId, page, size);
        return ResponseEntity.ok(this.siteApiMapper.asWorkspaceSitesResponseDTO(response));
    }

    private void validateRequest(final Long userId, final Integer page, final Integer size) {
        if (Objects.isNull(userId)) {
            throw new IllegalArgumentException("userId is required");
        }
        if (Objects.isNull(size) || !Objects.equals(size, 20)) {
            throw new IllegalArgumentException("size must be 20");
        }
        if (page < 0) {
            throw new IllegalArgumentException("page must be >= 0");
        }
    }
}
