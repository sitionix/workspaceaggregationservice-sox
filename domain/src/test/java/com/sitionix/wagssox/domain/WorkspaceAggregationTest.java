package com.sitionix.wagssox.domain;

import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkspaceAggregationTest {

    @Test
    void givenIdAndWorkspaceCode_whenCreateRecord_thenReturnRecordState() {
        //given
        final UUID id = UUID.fromString("e4e7701d-30bf-435c-91d9-d0f09e93a6fd");
        final String workspaceCode = "WS-123";
        final WorkspaceAggregation expected = new WorkspaceAggregation(id, workspaceCode);

        //when
        final WorkspaceAggregation actual = new WorkspaceAggregation(id, workspaceCode);

        //then
        assertThat(actual).isEqualTo(expected);
        assertThat(actual.id()).isEqualTo(id);
        assertThat(actual.workspaceCode()).isEqualTo(workspaceCode);
    }
}
