package com.canx.cebulax.cebulax.controller.body;

class ResponseBodyWrapper<T> {
    private final T data;

    public static <T> ResponseBodyWrapper<T> from(T data) {
        return new ResponseBodyWrapper<>(data);
    }

    private ResponseBodyWrapper(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
