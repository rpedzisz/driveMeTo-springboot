package com.makolab.cc.bootcampRP.service;

import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.repository.CourseRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Course Service class
 */
@Service
@Data
public class CourseService {
    private final CourseRepository courseRepository;

    /**
     * Method to calculate total fee of all FINISHED OR GRADED courses
     * @return
     */
    public Double calculateTotalFee(){
        List<Course> courses = courseRepository.findAll();
        Double totalFee = 0.0;
        for(Course course: courses){
            if(course.getCourseStatus() == CourseStatus.FINISHED
            || course.getCourseStatus() == CourseStatus.GRADED){
                totalFee+= course.getFee();
            }

        }
        return totalFee;
    }

}
