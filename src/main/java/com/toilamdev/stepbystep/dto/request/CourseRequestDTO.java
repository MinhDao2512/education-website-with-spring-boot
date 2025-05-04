package com.toilamdev.stepbystep.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CourseRequestDTO {
    @NotBlank(message = "Course title is required")
    @Size(max = 255, message = "Course title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Requirements are required")
    private String requirements;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be zero or a positive number")
    private Double price;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Tags are required")
    private Set<String> tags;
}
