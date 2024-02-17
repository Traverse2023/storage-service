package com.traverse.storage.utils.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.info("Not Found");
        ApiException error = ApiException.builder()
                .httpStatusCode(status.value())
                .timestamp(ZonedDateTime.now())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleBeanCreationException(BeanCreationException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException error = ApiException.builder()
                .message(e.getMessage())
                .timestamp(ZonedDateTime.now())
                .httpStatusCode(status.value())
                .build();
        return new ResponseEntity<>(error, status);
    }
}
