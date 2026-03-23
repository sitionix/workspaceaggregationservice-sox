package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.SiteOverviewDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SiteOverviewTypeApiMapperTest {

    private SiteOverviewTypeApiMapper siteOverviewTypeApiMapper;

    @BeforeEach
    void setUp() {
        this.siteOverviewTypeApiMapper = new SiteOverviewTypeApiMapperImpl();
    }

    @Test
    void givenType_whenMapType_thenReturnTypeEnum() {
        //given
        final WorkspaceSiteMetaType type = WorkspaceSiteMetaType.BLOG;

        //when
        final SiteOverviewDTO.TypeEnum actual = this.siteOverviewTypeApiMapper.mapType(type);

        //then
        assertThat(actual).isEqualTo(SiteOverviewDTO.TypeEnum.BLOG);
    }

    @Test
    void givenNullType_whenMapType_thenReturnNull() {
        //given
        final WorkspaceSiteMetaType type = null;

        //when
        final SiteOverviewDTO.TypeEnum actual = this.siteOverviewTypeApiMapper.mapType(type);

        //then
        assertThat(actual).isNull();
    }
}
