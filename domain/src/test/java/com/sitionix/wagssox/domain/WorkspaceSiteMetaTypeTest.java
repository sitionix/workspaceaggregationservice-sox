package com.sitionix.wagssox.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkspaceSiteMetaTypeTest {

    @Test
    void givenKnownId_whenFromId_thenReturnType() {
        //given
        final Long id = 4L;

        //when
        final WorkspaceSiteMetaType actual = WorkspaceSiteMetaType.fromId(id);

        //then
        assertThat(actual).isEqualTo(WorkspaceSiteMetaType.STORE);
    }

    @Test
    void givenNullId_whenFromId_thenReturnNull() {
        //given
        final Long id = null;

        //when
        final WorkspaceSiteMetaType actual = WorkspaceSiteMetaType.fromId(id);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenUnknownId_whenFromId_thenThrowIllegalArgumentException() {
        //given
        final Long id = 99L;

        //when //then
        assertThatThrownBy(() -> WorkspaceSiteMetaType.fromId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unknown WorkspaceSiteMetaType id: 99");
    }

    @Test
    void givenType_whenGetId_thenReturnId() {
        //given
        final WorkspaceSiteMetaType type = WorkspaceSiteMetaType.LANDING;

        //when
        final Long actual = type.getId();

        //then
        assertThat(actual).isEqualTo(5L);
    }
}
