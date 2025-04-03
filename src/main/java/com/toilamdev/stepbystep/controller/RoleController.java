package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.constant.ApiResponseExample;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.exception.GlobalException;
import com.toilamdev.stepbystep.service.impl.ResponseService;
import com.toilamdev.stepbystep.service.impl.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("${api.version}/roles")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Role Controller")
public class RoleController {
    private final RoleService roleService;
    private final ResponseService responseService;

    @Operation(summary = "Add New Roles", description = "Create New Roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = ApiResponseExample.CREATED_201))),
            @ApiResponse(responseCode = "409", description = "Fail",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = ApiResponseExample.CONFLICT_409))),
            @ApiResponse(responseCode = "400", description = "Fail",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = ApiResponseExample.BAD_REQUEST_400))),
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO> addNewRoles(HttpServletRequest request) {
        try{
            this.roleService.createNewRoles();
            return this.responseService.success(HttpStatus.CREATED, "Add new roles success.");
        }catch (GlobalException.NotCreateRolesException e){
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Add new roles fail",
                    Collections.singletonMap("error_message", e.getMessage()));
        }
    }
}
