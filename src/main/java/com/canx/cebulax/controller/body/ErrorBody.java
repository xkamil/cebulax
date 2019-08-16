package com.canx.cebulax.controller.body;

import java.util.List;

class ErrorBody {
    private int status;
    private final String message;
    private final String error;
    private List<SubError> errors;

    public ErrorBody(int status, String error, String message, List<SubError> errors) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.errors = errors;
    }

    public ErrorBody(int status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<SubError> getErrors() {
        return errors;
    }

}
