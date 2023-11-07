package com.makolab.cc.bootcampRP.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.makolab.cc.bootcampRP.repository.CourseRepository;
import com.makolab.cc.bootcampRP.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:tests.properties")
@Transactional
public class UserTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;


    private User user;
    @BeforeEach
    void init(){
        userRepository.deleteAll();
        courseRepository.deleteAll();

        user = new User();
        user.setUsername("Adam");
        user.setCurrentLocation("51.246454,22.523504");

    }

    @Test
    void deletingUserShouldNotDeleteCourses(){
        userRepository.save(user);
        Course course = new Course();
        course.setCourseGrade(5L);
        course.setUser(user);
        courseRepository.save(course);

        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course);

        user.setCourses(courses);

        userRepository.deleteById(user.getUserId());
        Assertions.assertNull(userRepository.findById(user.getUserId()).orElse(null));
        Assertions.assertNotNull(courseRepository.findById(course.getCourseId()).orElse(null));

    }




}
