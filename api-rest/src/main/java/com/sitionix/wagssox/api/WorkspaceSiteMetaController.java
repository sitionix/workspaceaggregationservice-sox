package com.sitionix.wagssox.api;

import com.sitionix.wagssox.api.dto.WorkspaceSiteMetaDTO;
import com.sitionix.wagssox.api.mapper.WorkspaceSiteMetaApiMapper;
import com.sitionix.wagssox.application.WorkspaceSiteMetaQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkspaceSiteMetaController {

    private final WorkspaceSiteMetaQuery workspaceSiteMetaQuery;
    private final WorkspaceSiteMetaApiMapper workspaceSiteMetaApiMapper;

    @GetMapping("/api/v1/workspace/sites")
    public List<WorkspaceSiteMetaDTO> getWorkspaceSites(@RequestParam("ownerUserId") final Long ownerUserId) {
        return this.workspaceSiteMetaApiMapper.asDtoList(this.workspaceSiteMetaQuery.findByOwnerUserId(ownerUserId));
    }
}
