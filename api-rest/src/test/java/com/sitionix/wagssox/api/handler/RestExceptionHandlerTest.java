package com.sitionix.wagssox.api.handler;

import com.app_afesox.wagssox.api_first.dto.ErrorDTO;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationResult;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

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
        final IllegalArgumentException exception = new IllegalArgumentException("size must be greater than 0");
        final ResponseEntity<ErrorDTO> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .code(400)
                        .title("Bad Request")
                        .details("size must be greater than 0")
                        .build());

        //when
        final ResponseEntity<ErrorDTO> actual = this.restExceptionHandler.handleIllegalArgumentException(exception);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenMissingSizeParameter_whenHandleMissingServletRequestParameterException_thenReturnBadRequestErrorDto() {
        //given
        final MissingServletRequestParameterException exception = new MissingServletRequestParameterException("size", "Integer");
        final ResponseEntity<ErrorDTO> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .code(400)
                        .title("Bad Request")
                        .details(exception.getMessage())
                        .build());

        //when
        final ResponseEntity<ErrorDTO> actual = this.restExceptionHandler
                .handleMissingServletRequestParameterException(exception);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenSizeValidationFailure_whenHandleHandlerMethodValidationException_thenReturnBadRequestErrorDto() throws Exception {
        //given
        final HandlerMethodValidationException exception = this.getValidationException(
                1,
                0,
                "must be greater than or equal to 1"
        );
        final ResponseEntity<ErrorDTO> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .code(400)
                        .title("Bad Request")
                        .details("must be greater than or equal to 1")
                        .build());

        //when
        final ResponseEntity<ErrorDTO> actual = this.restExceptionHandler
                .handleHandlerMethodValidationException(exception);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenPageValidationFailure_whenHandleHandlerMethodValidationException_thenReturnBadRequestErrorDto() throws Exception {
        //given
        final HandlerMethodValidationException exception = this.getValidationException(
                0,
                -1,
                "must be greater than or equal to 0"
        );
        final ResponseEntity<ErrorDTO> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .code(400)
                        .title("Bad Request")
                        .details("must be greater than or equal to 0")
                        .build());

        //when
        final ResponseEntity<ErrorDTO> actual = this.restExceptionHandler
                .handleHandlerMethodValidationException(exception);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private HandlerMethodValidationException getValidationException(
            final Integer parameterIndex,
            final Object argument,
            final String message
    ) throws Exception {
        final Method method = ValidationTarget.class.getDeclaredMethod("getSites", Integer.class, Integer.class);
        final MethodParameter methodParameter = new MethodParameter(method, parameterIndex);
        final ParameterValidationResult validationResult = new ParameterValidationResult(
                methodParameter,
                argument,
                List.of(new DefaultMessageSourceResolvable(new String[]{"validation.error"}, null, message))
        );
        final MethodValidationResult methodValidationResult = MethodValidationResult.create(
                this,
                method,
                List.of(validationResult)
        );
        return new HandlerMethodValidationException(methodValidationResult);
    }

    private static final class ValidationTarget {
        void getSites(final Integer page, final Integer size) {
        }
    }
}
