package com.sitionix.wagssox.application;

import com.sitionix.wagssox.domain.WorkspaceSiteMeta;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import com.sitionix.wagssox.domain.repository.WorkspaceSiteMetaRepository;
import com.sitionix.wagssox.domain.usecase.GetWorkspaceSites;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkspaceSitesImpl implements GetWorkspaceSites {

    private static final String UNTITLED_SITE_NAME = "Untitled site";

    private final WorkspaceSiteMetaRepository workspaceSiteMetaRepository;

    @Override
    public WorkspaceSitesPage execute(final Long userId, final Integer page, final Integer size) {
        final List<WorkspaceSiteMeta> rawItems = this.workspaceSiteMetaRepository.findActiveByUserId(userId, page, size + 1);
        final boolean hasNext = rawItems.size() > size;
        final List<WorkspaceSiteMeta> items = rawItems.stream()
                .limit(size)
                .map(this::applyNameFallback)
                .toList();

        return WorkspaceSitesPage.builder()
                .items(items)
                .page(page)
                .size(size)
                .hasNext(hasNext)
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
}
