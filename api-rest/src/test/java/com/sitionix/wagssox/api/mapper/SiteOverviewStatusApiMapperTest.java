package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.SiteOverviewDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SiteOverviewStatusApiMapperTest {

    private SiteOverviewStatusApiMapper siteOverviewStatusApiMapper;

    @BeforeEach
    void setUp() {
        this.siteOverviewStatusApiMapper = new SiteOverviewStatusApiMapperImpl();
    }

    @Test
    void givenStatus_whenMapStatus_thenReturnStatusEnum() {
        //given
        final WorkspaceSiteMetaStatus status = WorkspaceSiteMetaStatus.PUBLISHED;

        //when
        final SiteOverviewDTO.StatusEnum actual = this.siteOverviewStatusApiMapper.mapStatus(status);

        //then
        assertThat(actual).isEqualTo(SiteOverviewDTO.StatusEnum.PUBLISHED);
    }

    @Test
    void givenNullStatus_whenMapStatus_thenReturnNull() {
        //given
        final WorkspaceSiteMetaStatus status = null;

        //when
        final SiteOverviewDTO.StatusEnum actual = this.siteOverviewStatusApiMapper.mapStatus(status);

        //then
        assertThat(actual).isNull();
    }
}
