package com.canx.cebulax.cebulax.controller.body;

class ErrorBodyWrapper<T> {
    private final T error;

    public static <T> ErrorBodyWrapper<T> from(T error) {
        return new ErrorBodyWrapper<>(error);
    }

    private ErrorBodyWrapper(T error) {
        this.error = error;
    }

    public T getError() {
        return error;
    }
}
