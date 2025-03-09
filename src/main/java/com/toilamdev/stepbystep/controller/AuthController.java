package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.constant.ApiResponseExample;
import com.toilamdev.stepbystep.dto.request.UserRegisterDTO;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.entity.User;
import com.toilamdev.stepbystep.service.impl.ResponseService;
import com.toilamdev.stepbystep.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller")
public class AuthController {
    private final ResponseService responseService;
    private final UserService userService;

    @Operation(summary = "Register Account", description = "Create New User")
    @ApiResponses({
            @ApiResponse(description = "Created", responseCode = "201", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.CREATED_201)
            )),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.BAD_REQUEST_400)
            ))
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO> registerAccount(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        User user = this.userService.saveUser(userRegisterDTO);

        return this.responseService.success(HttpStatus.CREATED, "Create new account successfully",
                Collections.singletonMap("user_id", user.getId()));
    }
}
