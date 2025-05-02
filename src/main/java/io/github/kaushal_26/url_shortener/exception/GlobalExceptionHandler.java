package io.github.kaushal_26.url_shortener.exception;

import io.github.kaushal_26.url_shortener.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse createErrorResponse(ErrorCode errorCode, String details) {
        return ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .error(ErrorResponse.ErrorDetails.builder()
                        .status(errorCode.getStatusCode().value())
                        .message(errorCode.getMessage())
                        .details(details)
                        .build()
                )
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(this.createErrorResponse(ErrorCode.GENERIC_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(GenericException ex) {
        return ResponseEntity.status(ex.getErrorCode().getStatusCode())
                .body(this.createErrorResponse(ex.getErrorCode(), ex.getMessage()));
    }

}
