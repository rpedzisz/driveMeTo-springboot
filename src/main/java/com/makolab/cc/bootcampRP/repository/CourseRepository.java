package com.makolab.cc.bootcampRP.repository;

import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Course Repository interface
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByUserAndCourseStatus(User user, CourseStatus courseStatus);
    List<Course> findAllByCourseStatus(CourseStatus courseStatus);
    List<Course> findAllByDriver(Driver driver);
}
