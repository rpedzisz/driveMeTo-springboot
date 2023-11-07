package com.makolab.cc.bootcampRP.service;


import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @InjectMocks
    CourseService courseService;

    @Mock
    CourseRepository courseRepository;

    @Test
    void totalFeeShouldReturn70(){

        Course course1 = new Course();
        course1.setDistance(6.0);
        course1.setFee(30.0);
        course1.setCourseStatus(CourseStatus.FINISHED);

        Course course2 = new Course();
        course2.setDistance(8.0);
        course2.setFee(40.0);
        course2.setCourseStatus(CourseStatus.GRADED);

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        when(courseRepository.findAll()).thenReturn(courses);

        Double result = courseService.calculateTotalFee();

        Assertions.assertEquals(70, result);
    }


}
