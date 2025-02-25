package com.toilamdev.stepbystep.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ApiResponseDTO {
    private final boolean success;
    private final Instant timestamp;
    private final int status;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String path;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Object data;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Object errors;

    ApiResponseDTO(ApiResponseDTOBuilder builder) {
        this.success = builder.success;
        this.timestamp = builder.timestamp;
        this.status = builder.status;
        this.message = builder.message;
        this.path = builder.path;
        this.data = builder.data;
        this.errors = builder.errors;
    }

    public static ApiResponseDTOBuilder builder() {
        return new ApiResponseDTOBuilder();
    }

    @Getter
    public static class ApiResponseDTOBuilder {
        private boolean success;
        private Instant timestamp;
        private int status;
        private String message;
        private String path;
        private Object data;
        private Object errors;

        public ApiResponseDTOBuilder setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public ApiResponseDTOBuilder setTimestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ApiResponseDTOBuilder setStatus(int status) {
            this.status = status;
            return this;
        }

        public ApiResponseDTOBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseDTOBuilder setPath(String path) {
            this.path = path;
            return this;
        }

        public ApiResponseDTOBuilder setData(Object data) {
            this.data = data;
            return this;
        }

        public ApiResponseDTOBuilder setErrors(Object errors) {
            this.errors = errors;
            return this;
        }

        public ApiResponseDTO build() {
            return new ApiResponseDTO(this);
        }
    }
}
