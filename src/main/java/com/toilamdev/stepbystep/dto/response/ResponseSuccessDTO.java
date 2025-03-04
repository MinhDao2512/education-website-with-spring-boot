package com.toilamdev.stepbystep.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;

@Getter
public class ResponseSuccessDTO<T> implements Serializable {
    private final boolean success = true;
    private final int status;
    private final Instant timestamp = Instant.now();
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    ResponseSuccessDTO(Builder<T> builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.data = builder.data;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    @Getter
    public static class Builder<T> {
        private int status;
        private String message;
        private T data;

        private Builder() {
        }

        public Builder<T> status(int status) {
            this.status = status;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseSuccessDTO<T> build() {
            return new ResponseSuccessDTO<>(this);
        }
    }
}
