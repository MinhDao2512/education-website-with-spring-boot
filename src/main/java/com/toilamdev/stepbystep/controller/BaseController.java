package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public abstract class BaseController {
    protected <T> ResponseEntity<ApiResponseDTO> success(HttpStatus httpStatus, String message, T... data) {
        ApiResponseDTO response = ApiResponseDTO.builder()
                .setSuccess(true)
                .setStatus(httpStatus.value())
                .setTimestamp(Instant.now())
                .setMessage(message)
                .setData(data)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    protected <T> ResponseEntity<ApiResponseDTO> fail(HttpServletRequest request,
                                                      HttpStatus httpStatus,
                                                      String message,
                                                      T... errors) {
        ApiResponseDTO response = ApiResponseDTO.builder()
                .setSuccess(false)
                .setStatus(httpStatus.value())
                .setTimestamp(Instant.now())
                .setMessage(message)
                .setPath(request.getRequestURI())
                .setErrors(errors)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }
}
