package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Tag(name = "User Controller")
public class UserController extends BaseController {
    @Operation(summary = "Get users", description = "Get all users")
    @GetMapping
    public ResponseEntity<ApiResponseDTO> getAllUsers() {
        User user = new User();
        user.setFirstName("Dao");
        user.setLastName("Nguyen Minh");
        return success(HttpStatus.OK, "Get list users successfully", user);
    }

    @Operation(summary = "Get user", description = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getUser(@PathVariable @Min(1) int id) {
        User user = new User();
        user.setFirstName("Dao");
        user.setLastName("Nguyen Minh");
        return success(HttpStatus.OK, "Get user successfully", user);
    }
}
