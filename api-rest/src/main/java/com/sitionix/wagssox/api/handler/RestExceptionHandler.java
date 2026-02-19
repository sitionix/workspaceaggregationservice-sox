package com.sitionix.wagssox.api.handler;

import com.app_afesox.wagssox.api_first.dto.ErrorDTO;
import java.util.Objects;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorDTO> handleHandlerMethodValidationException(final HandlerMethodValidationException ex) {
        final String details = ex.getAllValidationResults().stream()
                .findFirst()
                .map(result -> {
                    final int parameterIndex = result.getMethodParameter().getParameterIndex();
                    if (Objects.equals(parameterIndex, 1)) {
                        return "size must be 20";
                    }
                    if (Objects.equals(parameterIndex, 2)) {
                        return "page must be >= 0";
                    }
                    return result.getResolvableErrors().stream()
                            .findFirst()
                            .map(MessageSourceResolvable::getDefaultMessage)
                            .orElse("Validation failed");
                })
                .orElse("Validation failed");
        return this.buildError(HttpStatus.BAD_REQUEST, details);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDTO> handleMissingServletRequestParameterException(
            final MissingServletRequestParameterException ex
    ) {
        if (Objects.equals(ex.getParameterName(), "userId")) {
            return this.buildError(HttpStatus.BAD_REQUEST, "userId is required");
        }
        if (Objects.equals(ex.getParameterName(), "size")) {
            return this.buildError(HttpStatus.BAD_REQUEST, "size is required");
        }
        return this.buildError(HttpStatus.BAD_REQUEST, "Required request parameter is missing");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(final IllegalArgumentException ex) {
        return this.buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<ErrorDTO> buildError(final HttpStatus status, final String details) {
        return ResponseEntity.status(status)
                .body(ErrorDTO.builder()
                        .code(status.value())
                        .title(status.getReasonPhrase())
                        .details(details)
                        .build());
    }
}
