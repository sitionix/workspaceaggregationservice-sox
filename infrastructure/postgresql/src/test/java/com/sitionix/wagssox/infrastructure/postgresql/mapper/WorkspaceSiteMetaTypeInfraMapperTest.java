package com.sitionix.wagssox.infrastructure.postgresql.mapper;

import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import com.sitionix.wagssox.infrastructure.postgresql.entity.WorkspaceSiteMetaTypeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkspaceSiteMetaTypeInfraMapperTest {

    private WorkspaceSiteMetaTypeInfraMapper workspaceSiteMetaTypeInfraMapper;

    @BeforeEach
    void setUp() {
        this.workspaceSiteMetaTypeInfraMapper = new WorkspaceSiteMetaTypeInfraMapperImpl();
    }

    @Test
    void givenTypeEntity_whenAsType_thenReturnType() {
        //given
        final WorkspaceSiteMetaTypeEntity typeEntity = this.getTypeEntity(3L, "BLOG");

        //when
        final WorkspaceSiteMetaType actual = this.workspaceSiteMetaTypeInfraMapper.asType(typeEntity);

        //then
        assertThat(actual).isEqualTo(WorkspaceSiteMetaType.BLOG);
    }

    @Test
    void givenNullTypeEntity_whenAsType_thenReturnNull() {
        //given
        final WorkspaceSiteMetaTypeEntity typeEntity = null;

        //when
        final WorkspaceSiteMetaType actual = this.workspaceSiteMetaTypeInfraMapper.asType(typeEntity);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenType_whenAsTypeEntity_thenReturnTypeEntity() {
        //given
        final WorkspaceSiteMetaType type = WorkspaceSiteMetaType.OTHER;
        final WorkspaceSiteMetaTypeEntity expected = this.getTypeEntity(6L, "OTHER");

        //when
        final WorkspaceSiteMetaTypeEntity actual = this.workspaceSiteMetaTypeInfraMapper.asTypeEntity(type);

        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenNullType_whenAsTypeEntity_thenReturnNull() {
        //given
        final WorkspaceSiteMetaType type = null;

        //when
        final WorkspaceSiteMetaTypeEntity actual = this.workspaceSiteMetaTypeInfraMapper.asTypeEntity(type);

        //then
        assertThat(actual).isNull();
    }

    private WorkspaceSiteMetaTypeEntity getTypeEntity(final Long id, final String description) {
        return WorkspaceSiteMetaTypeEntity.builder()
                .id(id)
                .description(description)
                .build();
    }
}
