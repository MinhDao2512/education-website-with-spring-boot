package com.toilamdev.stepbystep.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JWTokenAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ApiResponseDTO responseDTO = ApiResponseDTO.builder()
                .message("Unauthorized")
                .path(request.getServletPath())
                .status(HttpStatus.UNAUTHORIZED.value())
                .errorDetails(Collections.singletonMap("message_error", authException.getMessage()))
                .success(false)
                .timestamp(Instant.now())
                .build();

        this.objectMapper.writeValue(response.getOutputStream(),responseDTO);
    }
}
