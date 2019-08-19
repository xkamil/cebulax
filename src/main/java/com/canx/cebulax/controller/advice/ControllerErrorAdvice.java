package com.canx.cebulax.controller.advice;

import com.canx.cebulax.controller.body.ResponseFactory;
import com.canx.cebulax.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerErrorAdvice {

    private final ResponseFactory responseFactory;

    public ControllerErrorAdvice(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handle(MethodArgumentNotValidException ex) {
        return responseFactory.error(ex);
    }

    @ExceptionHandler(value = {EntityAlreadyExistsException.class})
    public ResponseEntity<?> handle(EntityAlreadyExistsException ex) {
        return responseFactory.error(ex);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<?> handle(EntityNotFoundException ex) {
        return responseFactory.error(ex);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<?> handle(BadCredentialsException ex) {
        return responseFactory.error(ex);
    }

    @ExceptionHandler(value = {InvalidArgumentException.class})
    public ResponseEntity<?> handle(InvalidArgumentException ex) {
        return responseFactory.error(ex);
    }

    @ExceptionHandler(value = {InvalidActionException.class})
    public ResponseEntity<?> handle(InvalidActionException ex) {
        return responseFactory.error(ex);
    }

}
