package com.canx.cebulax.controller.body;

import com.canx.cebulax.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseFactory {

    public ResponseEntity<?> ok(Object body) {
        return new ResponseEntity<>(ResponseBodyWrapper.from(body), HttpStatus.OK);
    }

    public ResponseEntity<?> ok() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> created(Object body) {
        return new ResponseEntity<>(ResponseBodyWrapper.from(body), HttpStatus.CREATED);
    }

    public ResponseEntity<?> error(ServiceException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getCode());
        ErrorBody errorBody = new ErrorBody(ex.getCode(), ex.getMessage(), ex.getError());
        Object body = ResponseBodyWrapper.from(errorBody);
        return new ResponseEntity<>(body, status);
    }

    public ResponseEntity<?> error(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<SubError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(SubError::from)
                .collect(Collectors.toList());

        ErrorBody error = new ErrorBody(status.value(), "validation_error", "Validation errors", errors);
        Object body = ResponseBodyWrapper.from(error);

        return new ResponseEntity<>(body, status);
    }

}
