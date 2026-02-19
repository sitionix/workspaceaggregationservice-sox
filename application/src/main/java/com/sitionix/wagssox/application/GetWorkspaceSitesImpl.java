package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaSlice;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.exception.AuthenticationRequiredException;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import com.sitionix.wagssox.domain.usecase.GetWorkspaceSites;
import com.sitionix.forge.security.server.user.ForgeUserClient;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkspaceSitesImpl implements GetWorkspaceSites {

    private static final String UNTITLED_SITE_NAME = "Untitled site";

    private final WorkspaceSiteMetaRepository workspaceSiteMetaRepository;
    private final ForgeUserClient forgeUserClient;

    @Override
    public WorkspaceSitesPage execute(final Integer page, final Integer size) {
        final Long userId = this.getUserId();
        final WorkspaceSiteMetaSlice activeSitesSlice = this.workspaceSiteMetaRepository.findActiveByUserId(userId, page, size);
        final List<WorkspaceSiteMeta> items = activeSitesSlice.getItems().stream()
                .map(this::applyNameFallback)
                .toList();

        return WorkspaceSitesPage.builder()
                .items(items)
                .page(page)
                .size(size)
                .hasNext(activeSitesSlice.getHasNext())
                .build();
    }

    private WorkspaceSiteMeta applyNameFallback(final WorkspaceSiteMeta siteMeta) {
        if (Objects.isNull(siteMeta.getName())) {
            return siteMeta.toBuilder()
                    .name(UNTITLED_SITE_NAME)
                    .build();
        }
        return siteMeta;
    }

    private Long getUserId() {
        try {
            return this.forgeUserClient.getUserId();
        } catch (final RuntimeException exception) {
            throw new AuthenticationRequiredException("Authentication required");
        }
    }
}
