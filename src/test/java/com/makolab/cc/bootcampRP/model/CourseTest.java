package com.makolab.cc.bootcampRP.model;

import com.makolab.cc.bootcampRP.repository.CarRepository;
import com.makolab.cc.bootcampRP.repository.CourseRepository;
import com.makolab.cc.bootcampRP.repository.DriverRepository;
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

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:tests.properties")
@Transactional
public class CourseTest {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DriverRepository driverRepository;

    Course course;
    @BeforeEach
    void init(){
        courseRepository.deleteAll();
        course = new Course();
        course.setFee(100.0);
        course.setCourseGrade(5L);

    }

    @Test
    void savingCourseWithStatusShouldConnectCourseStatus(){
        course.setCourseStatus(CourseStatus.FINISHED);
        courseRepository.save(course);
        Course actualCourse = courseRepository.findById(course.getCourseId()).orElse(null);

        Assertions.assertNotNull(actualCourse);
        Assertions.assertEquals(CourseStatus.FINISHED, actualCourse.getCourseStatus());

    }

    @Test
    void deletingCourseShouldNotDeleteUserOrCarOrDriver(){
        Car car = new Car();
        car.setBrand("Mercedes");
        carRepository.save(car);

        User user = new User();
        user.setUsername("Adam");
        userRepository.save(user);

        Driver driver = new Driver();
        driver.setName("Michał");
        driverRepository.save(driver);

        car.setDriver(driver);
        driver.setCar(car);

        course.setUser(user);
        course.setCar(car);
        course.setDriver(driver);
        courseRepository.save(course);

        courseRepository.deleteById(course.getCourseId());

        Assertions.assertNull(courseRepository.findById(course.getCourseId()).orElse(null));
        Assertions.assertNotNull(userRepository.findById(user.getUserId()).orElse(null));
        Assertions.assertNotNull(carRepository.findById(car.getCarId()).orElse(null));
        Assertions.assertNotNull(driverRepository.findById(driver.getDriverId()).orElse(null));
    }


}
