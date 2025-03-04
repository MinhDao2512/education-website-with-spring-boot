package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.service.IResponseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ResponseService implements IResponseService {
    @Override
    public ResponseEntity<ApiResponseDTO> success(HttpStatus httpStatus, String message, Object... data) {
        ApiResponseDTO responseDTO = ApiResponseDTO.builder()
                .success(true)
                .status(httpStatus.value())
                .timestamp(Instant.now())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(httpStatus).body(responseDTO);
    }

    @Override
    public ResponseEntity<ApiResponseDTO> fail(HttpServletRequest request, HttpStatus httpStatus, String message,
                                               Object... errorDetails) {
        ApiResponseDTO responseDTO = ApiResponseDTO.builder()
                .success(false)
                .status(httpStatus.value())
                .timestamp(Instant.now())
                .message(message)
                .path(request.getRequestURI())
                .errorDetails(errorDetails)
                .build();
        return ResponseEntity.status(httpStatus).body(responseDTO);
    }
}
