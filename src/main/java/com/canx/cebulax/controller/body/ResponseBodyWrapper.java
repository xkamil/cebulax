package com.canx.cebulax.controller.body;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseBodyWrapper<T> {
    private final T data;
    private final T error;

    public static <T> ResponseBodyWrapper<T> from(T data) {
        if (data instanceof ErrorBody) {
            return new ResponseBodyWrapper<>(null, data);
        } else {
            return new ResponseBodyWrapper<>(data, null);
        }
    }

    private ResponseBodyWrapper(T data, T error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public T getError() {
        return error;
    }
}
