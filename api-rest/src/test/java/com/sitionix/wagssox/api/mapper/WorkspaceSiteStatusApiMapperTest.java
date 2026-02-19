package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.WorkspaceSiteCardDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkspaceSiteStatusApiMapperTest {

    private WorkspaceSiteStatusApiMapper workspaceSiteStatusApiMapper;

    @BeforeEach
    void setUp() {
        this.workspaceSiteStatusApiMapper = new WorkspaceSiteStatusApiMapperImpl();
    }

    @Test
    void givenStatus_whenMapStatus_thenReturnStatusEnum() {
        //given
        final WorkspaceSiteMetaStatus status = WorkspaceSiteMetaStatus.PUBLISHED;

        //when
        final WorkspaceSiteCardDTO.StatusEnum actual = this.workspaceSiteStatusApiMapper.mapStatus(status);

        //then
        assertThat(actual).isEqualTo(WorkspaceSiteCardDTO.StatusEnum.PUBLISHED);
    }

    @Test
    void givenNullStatus_whenMapStatus_thenReturnNull() {
        //given
        final WorkspaceSiteMetaStatus status = null;

        //when
        final WorkspaceSiteCardDTO.StatusEnum actual = this.workspaceSiteStatusApiMapper.mapStatus(status);

        //then
        assertThat(actual).isNull();
    }
}
