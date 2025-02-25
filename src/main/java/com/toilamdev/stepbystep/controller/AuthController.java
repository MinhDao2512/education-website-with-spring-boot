package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.dto.request.UserLoginDTO;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller")
public class AuthController extends BaseController {
    @Operation(summary = "Login", description = "Login with Email and Password")
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(@Valid @RequestBody UserLoginDTO userLogin) {
        return success(HttpStatus.OK, "Login Success.");
    }
}
