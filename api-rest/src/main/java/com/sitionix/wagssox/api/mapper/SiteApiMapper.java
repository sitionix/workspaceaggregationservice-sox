package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.WorkspaceSiteCardDTO;
import com.app_afesox.wagssox.api_first.dto.WorkspaceSitesPageDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.domain.WorkspaceSitesPage;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SiteApiMapper {

    WorkspaceSitesPageDTO asWorkspaceSitesPageDTO(WorkspaceSitesPage src);

    default WorkspaceSitesPageDTO.SizeEnum asSizeEnum(final Integer src) {
        if (Objects.isNull(src)) {
            return null;
        }
        return WorkspaceSitesPageDTO.SizeEnum.fromValue(src);
    }

    default WorkspaceSiteCardDTO.StatusEnum asStatusEnum(final WorkspaceSiteMetaStatus src) {
        if (Objects.isNull(src)) {
            return null;
        }
        return WorkspaceSiteCardDTO.StatusEnum.fromValue(src.name());
    }

    default JsonNullable<WorkspaceSiteCardDTO.TypeEnum> asType(final WorkspaceSiteMetaType src) {
        if (Objects.isNull(src)) {
            return JsonNullable.of(null);
        }
        return JsonNullable.of(WorkspaceSiteCardDTO.TypeEnum.fromValue(src.name()));
    }

    default JsonNullable<String> asDescription(final String src) {
        return JsonNullable.of(src);
    }

    default OffsetDateTime asOffsetDateTime(final Instant src) {
        if (Objects.isNull(src)) {
            return null;
        }
        return src.atOffset(ZoneOffset.UTC);
    }
}
