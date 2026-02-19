package com.sitionix.wagssox.api.handler;

import com.sitionix.wagssox.api.dto.ErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class RestExceptionHandlerTest {

    private RestExceptionHandler restExceptionHandler;

    @BeforeEach
    void setUp() {
        this.restExceptionHandler = new RestExceptionHandler();
    }

    @Test
    void givenIllegalArgumentException_whenHandleIllegalArgumentException_thenReturnBadRequestErrorDto() {
        //given
        final IllegalArgumentException exception = new IllegalArgumentException("size must be 20");
        final ResponseEntity<ErrorDTO> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .code(400)
                        .title("Bad Request")
                        .details("size must be 20")
                        .build());

        //when
        final ResponseEntity<ErrorDTO> actual = this.restExceptionHandler.handleIllegalArgumentException(exception);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
