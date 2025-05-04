package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.dto.request.CourseRequestDTO;
import com.toilamdev.stepbystep.entity.Category;
import com.toilamdev.stepbystep.entity.Course;
import com.toilamdev.stepbystep.entity.Tag;
import com.toilamdev.stepbystep.entity.User;
import com.toilamdev.stepbystep.enums.CourseStatus;
import com.toilamdev.stepbystep.exception.GlobalException;
import com.toilamdev.stepbystep.repository.CategoryRepository;
import com.toilamdev.stepbystep.repository.CourseRepository;
import com.toilamdev.stepbystep.repository.TagRepository;
import com.toilamdev.stepbystep.repository.UserRepository;
import com.toilamdev.stepbystep.service.ICourseService;
import com.toilamdev.stepbystep.utils.FormatUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Override
    public int createNewCourse(CourseRequestDTO courseRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = this.userRepository.findUserByEmail(authentication.getName()).orElseThrow(
                    () -> new UsernameNotFoundException(String.format("Không tìm thấy User phù hợp với email: %s",
                            authentication.getName()))
            );

            if (currentUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))
                    && !currentUser.getIsInstructor()) {
                throw new IllegalStateException("Bạn cần phải trở thành Instructor mới có thể tạo khóa học");
            }

            Category category = this.categoryRepository.findBySlug(courseRequestDTO.getCategory())
                    .orElseThrow(
                            () -> new GlobalException.CategoryNotFoundException("Không tìm thấy Category phù hợp")
                    );

            Set<Tag> tags = courseRequestDTO.getTags().stream()
                    .map(slugTag -> tagRepository.findBySlug(slugTag).orElseThrow(
                            () -> new GlobalException.TagNotFoundException("Không tìm thấy Tag phù hợp"))
                    )
                    .collect(Collectors.toSet());

            Course newCourse = Course.builder()
                    .title(FormatUtils.formatName(courseRequestDTO.getTitle()))
                    .description(courseRequestDTO.getDescription())
                    .requirements(courseRequestDTO.getRequirements())
                    .price(courseRequestDTO.getPrice())
                    .status(CourseStatus.DRAFT)
                    .category(category)
                    .instructor(currentUser)
                    .tags(tags)
                    .build();

            newCourse = courseRepository.save(newCourse);
            return newCourse.getId();
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
