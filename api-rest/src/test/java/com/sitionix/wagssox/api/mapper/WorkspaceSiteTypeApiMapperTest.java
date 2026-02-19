package com.sitionix.wagssox.api.mapper;

import com.app_afesox.wagssox.api_first.dto.WorkspaceSiteCardDTO;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkspaceSiteTypeApiMapperTest {

    private WorkspaceSiteTypeApiMapper workspaceSiteTypeApiMapper;

    @BeforeEach
    void setUp() {
        this.workspaceSiteTypeApiMapper = new WorkspaceSiteTypeApiMapperImpl();
    }

    @Test
    void givenType_whenMapType_thenReturnTypeEnum() {
        //given
        final WorkspaceSiteMetaType type = WorkspaceSiteMetaType.BLOG;

        //when
        final WorkspaceSiteCardDTO.TypeEnum actual = this.workspaceSiteTypeApiMapper.mapType(type);

        //then
        assertThat(actual).isEqualTo(WorkspaceSiteCardDTO.TypeEnum.BLOG);
    }

    @Test
    void givenNullType_whenMapType_thenReturnNull() {
        //given
        final WorkspaceSiteMetaType type = null;

        //when
        final WorkspaceSiteCardDTO.TypeEnum actual = this.workspaceSiteTypeApiMapper.mapType(type);

        //then
        assertThat(actual).isNull();
    }
}
