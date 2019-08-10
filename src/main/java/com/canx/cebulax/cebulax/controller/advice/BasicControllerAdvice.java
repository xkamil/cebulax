package com.canx.cebulax.cebulax.controller.advice;

import com.canx.cebulax.cebulax.controller.body.ErrorBody;
import com.canx.cebulax.cebulax.controller.body.ErrorBodyWrapper;
import com.canx.cebulax.cebulax.controller.body.SubError;
import com.canx.cebulax.cebulax.exception.BadCredentialsException;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class BasicControllerAdvice {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleInvalidArgument(MethodArgumentNotValidException ex) {
        int status = HttpStatus.BAD_REQUEST.value();

        List<SubError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(SubError::from)
                .collect(Collectors.toList());

        ErrorBody error = new ErrorBody(status, "validation_error", "Validation errors", errors);

        return ResponseEntity.status(status).body(ErrorBodyWrapper.from(error));
    }

    @ExceptionHandler(value = {EntityAlreadyExistsException.class, EntityNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<Object> handleNotFoundAndConflict(RuntimeException ex) {
        int status = HttpStatus.BAD_REQUEST.value();
        String error = "";

        if (ex instanceof EntityAlreadyExistsException) {
            error = "conflict";
        } else if (ex instanceof EntityNotFoundException) {
            error = "not_found";
        } else if (ex instanceof BadCredentialsException) {
            error = "bad_credentials";
        }

        ErrorBody errorBody = new ErrorBody(status, error, ex.getMessage(), new ArrayList<>());

        return ResponseEntity.status(status).body(ErrorBodyWrapper.from(errorBody));
    }

}
