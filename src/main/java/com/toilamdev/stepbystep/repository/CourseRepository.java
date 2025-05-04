package com.toilamdev.stepbystep.repository;

import com.toilamdev.stepbystep.entity.Course;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.sections s WHERE c.id = :courseId AND s.isDeleted = false")
    Optional<Course> findCourseWithActiveSections(@Param("courseId") Integer courseId);
}
