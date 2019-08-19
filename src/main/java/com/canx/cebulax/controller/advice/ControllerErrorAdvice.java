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

    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<?> handle(ServiceException ex) {
        return responseFactory.error(ex);
    }

}
