package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.constant.ApiResponseExample;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.entity.Role;
import com.toilamdev.stepbystep.enums.RoleName;
import com.toilamdev.stepbystep.service.impl.ResponseService;
import com.toilamdev.stepbystep.service.impl.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.version}/roles")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Role Controller")
public class RoleController {
    private final RoleService roleService;
    private final ResponseService responseService;

    @Operation(summary = "Get all roles", description = "Find all roles")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = ApiResponseExample.OK_200)))
    )
    @GetMapping
    public ResponseEntity<ApiResponseDTO> listAllRoles() {
        List<Role> results = this.roleService.getAllRoles();
        Map<RoleName, String> data = results.stream()
                .collect(Collectors.toMap(Role::getName, Role::getDescription));

        return this.responseService.success(HttpStatus.OK, "Get list all roles successfully.", data);
    }
}
