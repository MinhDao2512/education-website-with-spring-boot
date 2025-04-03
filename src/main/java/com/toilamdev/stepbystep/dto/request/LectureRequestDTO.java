package com.toilamdev.stepbystep.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureRequestDTO {
    @NotBlank(message = "Lecture title is required")
    @Size(max = 255, message = "Lecture title must not exceed 255 characters")
    private String lectureTitle;

    private String document;

    @NotNull(message = "Section id is required")
    @Min(value = 1, message = "Section id must not < 1")
    private Integer sectionId;
}
