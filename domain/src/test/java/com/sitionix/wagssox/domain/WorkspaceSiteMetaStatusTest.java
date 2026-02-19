package com.sitionix.wagssox.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkspaceSiteMetaStatusTest {

    @Test
    void givenKnownId_whenFromId_thenReturnStatus() {
        //given
        final Long id = 2L;

        //when
        final WorkspaceSiteMetaStatus actual = WorkspaceSiteMetaStatus.fromId(id);

        //then
        assertThat(actual).isEqualTo(WorkspaceSiteMetaStatus.PUBLISHED);
    }

    @Test
    void givenNullId_whenFromId_thenReturnNull() {
        //given
        final Long id = null;

        //when
        final WorkspaceSiteMetaStatus actual = WorkspaceSiteMetaStatus.fromId(id);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenUnknownId_whenFromId_thenThrowIllegalArgumentException() {
        //given
        final Long id = 99L;

        //when //then
        assertThatThrownBy(() -> WorkspaceSiteMetaStatus.fromId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unknown WorkspaceSiteMetaStatus id: 99");
    }

    @Test
    void givenStatus_whenGetId_thenReturnId() {
        //given
        final WorkspaceSiteMetaStatus status = WorkspaceSiteMetaStatus.ARCHIVED;

        //when
        final Long actual = status.getId();

        //then
        assertThat(actual).isEqualTo(3L);
    }
}
