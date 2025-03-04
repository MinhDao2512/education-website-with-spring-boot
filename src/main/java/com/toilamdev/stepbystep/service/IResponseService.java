package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IResponseService {
    ResponseEntity<ApiResponseDTO> success(HttpStatus httpStatus, String message, Object... data);

    ResponseEntity<ApiResponseDTO> fail(HttpServletRequest request, HttpStatus httpStatus, String message,
                                        Object... errorDetails);
}
