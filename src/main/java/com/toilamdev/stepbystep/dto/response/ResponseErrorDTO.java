package com.toilamdev.stepbystep.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;

@Getter
public class ResponseErrorDTO<T> implements Serializable {
    private final boolean success = true;
    private final int status;
    private final Instant timestamp = Instant.now();
    private final String messageError;
    private final String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T errors;

    ResponseErrorDTO(Builder<T> builder) {
        this.status = builder.status;
        this.messageError = builder.messageError;
        this.path = builder.path;
        this.errors = builder.errors;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    @Getter
    public static class Builder<T> {
        private int status;
        private String messageError;
        private String path;
        private T errors;

        public Builder<T> status(int status) {
            this.status = status;
            return this;
        }

        public Builder<T> messageError(String messageError) {
            this.messageError = messageError;
            return this;
        }

        public Builder<T> path(String path) {
            this.path = path;
            return this;
        }

        public Builder<T> errors(T errors) {
            this.errors = errors;
            return this;
        }

        public ResponseErrorDTO<T> build() {
            return new ResponseErrorDTO<>(this);
        }
    }
}
