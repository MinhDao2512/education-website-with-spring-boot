package com.toilamdev.stepbystep.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionRequestDTO {
    @NotBlank(message = "Section title is required")
    @Size(max = 255, message = "Section title must not exceed 255 characters")
    private String title;

    @Size(max = 255, message = "Objective must not exceed 255 characters")
    private String objective;

    @Min(value = 1, message = "Order of section must not < 1")
    @NotNull(message = "Order of section is required")
    private Integer orderOfSection;
}
