package com.sitionix.wagssox.api.handler;

import com.app_afesox.wagssox.api_first.dto.ErrorDTO;
import com.sitionix.wagssox.domain.exception.SiteOverviewNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorDTO> handleHandlerMethodValidationException(final HandlerMethodValidationException ex) {
        final String details = ex.getAllValidationResults().stream()
                .findFirst()
                .flatMap(result -> result.getResolvableErrors().stream().findFirst())
                .map(MessageSourceResolvable::getDefaultMessage)
                .orElse("Validation failed");
        return this.buildError(HttpStatus.BAD_REQUEST, details);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDTO> handleMissingServletRequestParameterException(
            final MissingServletRequestParameterException ex
    ) {
        return this.buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(final IllegalArgumentException ex) {
        return this.buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(final ConstraintViolationException ex) {
        final String details = ex.getConstraintViolations().stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("Validation failed");
        return this.buildError(HttpStatus.BAD_REQUEST, details);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
        if ("siteId".equals(ex.getName())) {
            return this.buildError(HttpStatus.BAD_REQUEST, "Invalid siteId");
        }
        return this.buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(SiteOverviewNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleSiteOverviewNotFoundException(final SiteOverviewNotFoundException ex) {
        return this.buildError(HttpStatus.NOT_FOUND, ex.getMessage());
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
