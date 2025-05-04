package com.toilamdev.stepbystep.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionUpdateRequestDTO {
    @NotBlank(message = "Section title is required")
    @Size(max = 255, message = "Section title must not exceed 255 characters")
    private String sectionTitle;

    @Size(max = 255, message = "Objective must not exceed 255 characters")
    private String objective;
}
