package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.constant.ApiResponseExample;
import com.toilamdev.stepbystep.dto.request.UserLoginRequestDTO;
import com.toilamdev.stepbystep.dto.request.UserRegisterRequestDTO;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.service.impl.ResponseService;
import com.toilamdev.stepbystep.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("${api.version}/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller")
public class AuthController {
    private final ResponseService responseService;
    private final UserService userService;

    @Operation(summary = "Register Account", description = "Create New User")
    @ApiResponses(value = {
            @ApiResponse(description = "Created", responseCode = "201", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.CREATED_201)
            )),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.BAD_REQUEST_400)
            )),
            @ApiResponse(description = "Conflict data", responseCode = "409", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.CONFLICT_409)
            ))
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO> registerAccount(@Valid @RequestBody UserRegisterRequestDTO userRegisterRequestDTO,
                                                          HttpServletRequest request) {
        try {
            return this.responseService.success(HttpStatus.CREATED, "Create new account success",
                    Collections.singletonMap("user_id", this.userService.saveUser(userRegisterRequestDTO)));
        } catch (RuntimeException e) {
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Create new account fail",
                    Collections.singletonMap("message_error", e.getMessage()));
        }
    }

    @Operation(summary = "Login Account", description = "Login with Username & Password")
    @ApiResponses(value = {
            @ApiResponse(description = "Login Success", responseCode = "200", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.OK_200)
            )),
            @ApiResponse(description = "Login Fail", responseCode = "401", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.UNAUTHORIZED_401)
            ))
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> loginAccount(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO,
                                                       HttpServletRequest request) {
        try {
            String token = this.userService.login(userLoginRequestDTO);
            return this.responseService.success(HttpStatus.OK, "Login success",
                    Collections.singletonMap("access-token", token));
        } catch (UsernameNotFoundException | BadCredentialsException | DisabledException e ) {
            return this.responseService.fail(request, HttpStatus.UNAUTHORIZED, "Login fail",
                    Collections.singletonMap("message", e.getMessage()));
        }
    }
}
