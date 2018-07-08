package br.com.jhonatanserafim.bankslips.core.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

public interface Endpoint {

    default <T> ResponseEntity<T> ok(T t) {
        return ResponseEntity.ok(t);
    }

    default <T> ResponseEntity<T> created(T t) {
        return ResponseEntity.status(CREATED)
                .contentType(APPLICATION_JSON_UTF8)
                .body(t);
    }

    default <T> ResponseEntity<T> noContent() {
        return status(NO_CONTENT);
    }

    default <T> ResponseEntity<T> notFound() {
        return status(NOT_FOUND);
    }

    default <T> ResponseEntity<T> badRequest() {
        return status(BAD_REQUEST);
    }

    default <T> ResponseEntity<T> unprocessableEntity() {
        return status(UNPROCESSABLE_ENTITY);
    }

    default <T> ResponseEntity<T> status(HttpStatus status) {
        return ResponseEntity.status(status)
                .contentType(APPLICATION_JSON_UTF8)
                .build();
    }
}
