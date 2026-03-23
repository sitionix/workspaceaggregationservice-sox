package com.sitionix.wagssox.api.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InstantApiMapperTest {

    private InstantApiMapper instantApiMapper;

    @BeforeEach
    void setUp() {
        this.instantApiMapper = new InstantApiMapperImpl();
    }

    @Test
    void givenInstant_whenToUtcOffsetDateTime_thenReturnUtcOffsetDateTime() {
        //given
        final Instant src = Instant.parse("2026-01-10T12:00:00Z");
        final OffsetDateTime expected = OffsetDateTime.parse("2026-01-10T12:00:00Z");

        //when
        final OffsetDateTime actual = this.instantApiMapper.toUtcOffsetDateTime(src);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullInstant_whenToUtcOffsetDateTime_thenReturnNull() {
        //given

        //when
        final OffsetDateTime actual = this.instantApiMapper.toUtcOffsetDateTime(null);

        //then
        assertThat(actual).isNull();
    }
}
