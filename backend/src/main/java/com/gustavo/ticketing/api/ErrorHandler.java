package com.gustavo.ticketing.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
    var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    pd.setTitle("Validation error");
    pd.setDetail("Request validation failed");
    pd.setProperty("timestamp", Instant.now().toString());

    var errors = new HashMap<String, String>();
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      errors.put(fe.getField(), fe.getDefaultMessage());
    }
    pd.setProperty("errors", errors);
    return pd;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ProblemDetail handleIllegalArg(IllegalArgumentException ex) {
    var pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
    pd.setTitle("Unauthorized");
    pd.setDetail(ex.getMessage());
    pd.setProperty("timestamp", Instant.now().toString());
    return pd;
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ProblemDetail handleConstraint(DataIntegrityViolationException ex) {
    var pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
    pd.setTitle("Constraint violation");
    pd.setDetail("The request violates a database constraint");
    pd.setProperty("timestamp", Instant.now().toString());
    return pd;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGeneric(Exception ex) {
    var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    pd.setTitle("Internal error");
    pd.setDetail("Unexpected error");
    pd.setProperty("timestamp", Instant.now().toString());
    return pd;
  }
}
