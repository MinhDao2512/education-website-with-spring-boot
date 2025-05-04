package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.constant.ApiResponseExample;
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
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("${api.version}/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller")
public class UserController {

    private final ResponseService responseService;
    private final UserService userService;

    @Operation(summary = "Get users", description = "Get list users")
    @ApiResponses({
            @ApiResponse(description = "Get list users successfully.", responseCode = "200", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.OK_200)
            ))
    })
    @GetMapping
    public ResponseEntity<ApiResponseDTO> getAllUsers() {
        return this.responseService.success(HttpStatus.OK, "Get list users successfully");
    }

    @Operation(summary = "Get user", description = "Get user by id")
    @ApiResponses({
            @ApiResponse(description = "Get user successfully.", responseCode = "200", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.OK_200)
            )),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.BAD_REQUEST_400)
            ))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getUser(@PathVariable @Min(1) int id) {
        return this.responseService.success(HttpStatus.OK, "Get user successfully");
    }

    @Operation(summary = "Update user", description = "Update user is instructor")
    @ApiResponses({
            @ApiResponse(description = "Update user successfully.", responseCode = "200", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.OK_200)
            )),
            @ApiResponse(description = "Update user fail", responseCode = "409", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.CONFLICT_409)
            ))
    })
    @PutMapping("/isInstructor")
    public ResponseEntity<ApiResponseDTO> putUserIsInstructor(HttpServletRequest request) {
        try {
            return this.responseService.success(HttpStatus.OK, "Update user is instructor success",
                    Collections.singletonMap("user_id", userService.updateUserIsInstructor()));
        } catch (RuntimeException e) {
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Update user is instructor fail",
                    Collections.singletonMap("message_error", e.getMessage()));
        }
    }
}
