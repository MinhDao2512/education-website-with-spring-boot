package com.toilamdev.stepbystep.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ApiResponseDTO {
    private final boolean success;
    private final int status;
    private final Instant timestamp;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object errorDetails;

    ApiResponseDTO(Builder builder) {
        this.success = builder.success;
        this.status = builder.status;
        this.timestamp = builder.timestamp;
        this.message = builder.message;
        this.path = builder.path;
        this.data = builder.data;
        this.errorDetails = builder.errorDetails;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Getter
    public static class Builder {
        private boolean success;
        private int status;
        private Instant timestamp;
        private String message;
        private String path;
        private Object data;
        private Object errorDetails;

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder errorDetails(Object errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        public ApiResponseDTO build() {
            return new ApiResponseDTO(this);
        }
    }
}
