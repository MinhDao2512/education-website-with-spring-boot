package com.toilamdev.stepbystep.exception;

import com.toilamdev.stepbystep.controller.BaseController;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponseDTO> handleErrorClient(Exception e, HttpServletRequest request) {
        if (e instanceof MethodArgumentNotValidException exception) {
            Map<String, String> errors = new HashMap<>();
            BindingResult bindingResult = exception.getBindingResult();
            bindingResult.getFieldErrors().forEach(fileError
                    -> errors.put(fileError.getField(), fileError.getDefaultMessage()));

            return fail(request, HttpStatus.BAD_REQUEST, "Login fail.", errors);
        } else {
            return fail(request, HttpStatus.BAD_REQUEST, "Id must not valid.",
                    Collections.singletonMap("error", e.getMessage()));
        }
    }
}
