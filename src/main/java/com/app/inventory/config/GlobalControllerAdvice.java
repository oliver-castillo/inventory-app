package com.app.inventory.config;

import org.openapitools.model.ErrorResponse;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@ControllerAdvice
public class GlobalControllerAdvice {
  @ExceptionHandler({NotFoundException.class, AlreadyExistsException.class})
  public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
    HttpStatus status = Optional.ofNullable(AnnotatedElementUtils
            .findMergedAnnotation(ex.getClass(), ResponseStatus.class))
        .map(ResponseStatus::value)
        .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, status);
  }
}
