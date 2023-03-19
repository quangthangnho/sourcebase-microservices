package com.br.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public abstract class BaseController {
    /**
     * To response entity with optional.
     *
     * @param optional optional.
     * @param <T>      T.
     * @return ResponseEntity.
     */
    @SuppressWarnings("all")
    protected <T> ResponseEntity<T> toResponseEntity(Optional<T> optional) {
        if (!optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return toResponseEntity(optional.get());
    }

    /**
     * To response entity with OK status.
     *
     * @param data data.
     * @param <T>  T.
     * @return ResponseEntity.
     */
    protected <T> ResponseEntity<T> toResponseEntity(T data) {
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


    /**
     * To response entity with status.
     *
     * @param data   data.
     * @param status http status.
     * @param <T>    T.
     * @return ResponseEntity.
     */
    protected <T> ResponseEntity<T> toResponseEntity(T data,
                                                     HttpStatus status) {
        return new ResponseEntity<>(data, status);
    }

    /**
     * To response entity with status.
     *
     * @param optional optional.
     * @param status   http status.
     * @param <T>      T.
     * @return ResponseEntity.
     */
    @SuppressWarnings("all")
    protected <T> ResponseEntity<T> toResponseEntity(Optional<T> optional, HttpStatus status) {
        return optional.map(t -> new ResponseEntity<>(t, status))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * To response entity with status.
     *
     * @return ResponseEntity.
     */
    protected <T> ResponseEntity<T> toNoBodyContentResponse() {
        return ResponseEntity.noContent().build();
    }

    /**
     * To response entity with OK status.
     *
     * @return ResponseEntity.
     */
    protected ResponseEntity<String> toOKResponse() {
        return new ResponseEntity<>("{\"status\": \"OK\"}", HttpStatus.OK);
    }
}
