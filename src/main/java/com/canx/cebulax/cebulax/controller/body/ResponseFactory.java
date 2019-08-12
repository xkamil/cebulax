package com.canx.cebulax.cebulax.controller.body;

import com.canx.cebulax.cebulax.exception.BadCredentialsException;
import com.canx.cebulax.cebulax.exception.EntityAlreadyExistsException;
import com.canx.cebulax.cebulax.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseFactory {

    public ResponseEntity<?> ok(Object body) {
        return createOk(body, HttpStatus.OK);
    }

    public ResponseEntity<?> ok() {
        return createOk("", HttpStatus.OK);
    }

    public ResponseEntity<?> created(Object body) {
        return createOk(body, HttpStatus.CREATED);
    }

    public ResponseEntity<?> error(EntityNotFoundException ex) {
        return createError(ex, "not_found");
    }

    public ResponseEntity<?> error(EntityAlreadyExistsException ex) {
        return createError(ex, "conflict");
    }

    public ResponseEntity<?> error(BadCredentialsException ex) {
        return createError(ex, "bad_credentials");
    }

    public ResponseEntity<?> error(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<SubError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(SubError::from)
                .collect(Collectors.toList());

        ErrorBody error = new ErrorBody(status.value(), "validation_error", "Validation errors", errors);
        Object body = ErrorBodyWrapper.from(error);

        return new ResponseEntity<>(body, status);
    }

    private ResponseEntity<?> createError(RuntimeException ex, String error) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorBody errorBody = new ErrorBody(status.value(), ex.getMessage(), error);
        Object body = ErrorBodyWrapper.from(errorBody);
        return new ResponseEntity<>(body, status);
    }

    private ResponseEntity<?> createOk(Object body, HttpStatus status) {
        return new ResponseEntity<>(ResponseBodyWrapper.from(body), status);
    }

}
