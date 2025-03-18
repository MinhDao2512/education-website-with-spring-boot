package com.toilamdev.stepbystep.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.version}/courses")
@Tag(name = "Course Controller")
public class CourseController {
}
