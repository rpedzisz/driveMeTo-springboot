package com.makolab.cc.bootcampRP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makolab.cc.bootcampRP.dto.CarDto;
import com.makolab.cc.bootcampRP.dto.DriverDto;
import com.makolab.cc.bootcampRP.mapper.CarMapper;
import com.makolab.cc.bootcampRP.mapper.DriverMapper;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.DriverLanguages;
import com.makolab.cc.bootcampRP.service.CarService;
import com.makolab.cc.bootcampRP.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Slf4j
@TestPropertySource(locations = "classpath:tests.properties")
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverService driverService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DriverMapper driverMapper;

    @Test
    public void findAllShouldReturnOK() throws Exception {
        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");


        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");
        driver.setCar(car);

        Car car1 = new Car();
        car1.setCarId(2L);
        car1.setBrand("Fiat");
        car1.setModel("125p");


        Driver driver1 = new Driver();
        driver1.setDriverId(2L);
        driver1.setName("Michal");
        driver1.setCar(car1);

        car.setDriver(driver);
        car1.setDriver(driver1);


        List<Driver> driverList = new ArrayList<>();
        driverList.add(driver);
        driverList.add(driver1);

        when(driverService.findAll()).thenReturn(driverList);

        MvcResult mvcResult = mockMvc
                .perform(get("/drivers"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "[{\"driverId\":1,\"name\":\"Adam\",\"carId\":1,\"avgGrade\":0.0}," +
                "{\"driverId\":2,\"name\":\"Michal\",\"carId\":2,\"avgGrade\":0.0}]";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);

    }
    @Test
    public void countDriversShouldReturnOK() throws Exception {


        when(driverService.calculateNumberOfDrivers()).thenReturn(3);

        MvcResult mvcResult = mockMvc
                .perform(get("/drivers/count"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "3";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);

    }

    @Test
    void addDriverShouldReturnOK() throws Exception {
        DriverDto dto = new DriverDto();
        dto.setName("Adam");

        Set<DriverLanguages> driverLanguagesSet = new HashSet<>();
        driverLanguagesSet.add(DriverLanguages.ENGLISH);
        dto.setDriverLanguagesSet(driverLanguagesSet);


        DriverDto returnDto = new DriverDto();
        returnDto.setName("Adam");
        returnDto.setDriverId(1L);
        returnDto.setDriverLanguagesSet(driverLanguagesSet);


        when(driverService.addNewDriver(any(Driver.class)))
                .thenReturn(driverMapper.addDriver_DtoToDriver(returnDto));


        MvcResult mvcResult = mockMvc
                .perform(post("/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn();

        String expectedBody = "{\"driverId\":1,\"name\":\"Adam\",\"driverLanguagesSet\":[\"ENGLISH\"]}";
        String actualBody = mvcResult.getResponse().getContentAsString();


        assertEquals(expectedBody, actualBody);

    }

    @Test
    void updateCarStatusShouldReturnOK() throws Exception {
        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        car.setCarStatus(CarStatus.DRIVING);

        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");
        driver.setCar(car);

        Car returnCar = new Car();
        returnCar.setCarId(1L);
        returnCar.setBrand("Mercedes");
        returnCar.setModel("Sedan");
        returnCar.setCarStatus(CarStatus.READY_TO_GO);


        when(driverService.updateCarStatus(1L, CarStatus.READY_TO_GO))
                .thenReturn(returnCar);


        MvcResult mvcResult = mockMvc
                .perform(post("/drivers/update-car-status/{driverId}/{carStatus}", 1L, "READY_TO_GO"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedBody = "{\"carId\":1,\"brand\":\"Mercedes\",\"model\":\"Sedan\",\"carStatus\":\"READY_TO_GO\"}";
        String actualBody = mvcResult.getResponse().getContentAsString();


        assertEquals(expectedBody, actualBody);

    }

    @Test
    void updateCarLocalizationShouldReturnOK() throws Exception {
        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        car.setCarStatus(CarStatus.DRIVING);
        car.setCurrentLocalization("51.254738,22.544501");

        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");
        driver.setCar(car);

        Car returnCar = new Car();
        returnCar.setCarId(1L);
        returnCar.setBrand("Mercedes");
        returnCar.setModel("Sedan");
        returnCar.setCurrentLocalization("51.253251,22.548378");


        when(driverService.updateCarLocalization(1L, "51.253251,22.548378"))
                .thenReturn(returnCar);


        MvcResult mvcResult = mockMvc
                .perform(post("/drivers/update-car-localization/{driverId}/{localization}",
                        1L, "51.253251,22.548378"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedBody = "{\"carId\":1,\"brand\":\"Mercedes\",\"model\":\"Sedan\"," +
                "\"currentLocalization\":\"51.253251,22.548378\"}";
        String actualBody = mvcResult.getResponse().getContentAsString();


        assertEquals(expectedBody, actualBody);

    }

    @Test
    void getCoursesForDriverShouldReturnOK() throws Exception {
        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");

        Course course = new Course();
        course.setCourseId(1L);
        course.setCourseGrade(5L);
        course.setDriver(driver);

        Course course1 = new Course();
        course1.setCourseId(2L);
        course1.setCourseGrade(5L);
        course1.setDriver(driver);

        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        courseList.add(course1);
        driver.setCourses(courseList);
        when(driverService.findAllCoursesForDriver(1L)).thenReturn(courseList);

        MvcResult mvcResult = mockMvc
                .perform(get("/drivers/get-courses-for-driver/{driverId}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        String expectedBody = "[{\"courseId\":1,\"fee\":0.0,\"courseGrade\":5,\"driverId\":1}," +
                "{\"courseId\":2,\"fee\":0.0,\"courseGrade\":5,\"driverId\":1}]";
        String actualBody = mvcResult.getResponse().getContentAsString();


        assertEquals(expectedBody, actualBody);

    }

    @Test
    void updateCourseStatusForDriverShouldReturnOK() throws Exception {
        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");

        Course course = new Course();
        course.setCourseId(1L);
        course.setCourseGrade(5L);
        course.setDriver(driver);
        course.setCourseStatus(CourseStatus.REQUESTED);


        Course updatedCourse = new Course();
        updatedCourse.setCourseId(1L);
        updatedCourse.setCourseGrade(5L);
        updatedCourse.setDriver(driver);
        updatedCourse.setCourseStatus(CourseStatus.FINISHED);

        when(driverService.updateCourseStatus(1L, 1L, CourseStatus.FINISHED)).thenReturn(updatedCourse);

        MvcResult mvcResult = mockMvc
                .perform(post("/drivers/update-course-status/{courseId}/{driverId}/{courseStatus}",
                        1L, 1L, "FINISHED"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedBody = "{\"courseId\":1,\"courseStatus\":\"FINISHED\",\"fee\":0.0,\"courseGrade\":5,\"driverId\":1}";
        String actualBody = mvcResult.getResponse().getContentAsString();


        assertEquals(expectedBody, actualBody);

    }

    @Test
    void assignDriverToCarShouldReturnOK() throws Exception {
        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");

        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");

        Car assignedCar = new Car();
        assignedCar.setCarId(1L);
        assignedCar.setBrand("Mercedes");
        assignedCar.setModel("Sedan");
        assignedCar.setDriver(driver);

        when(driverService.assignDriverToCar(1L, 1L)).thenReturn(assignedCar);

        MvcResult mvcResult = mockMvc
                .perform(post("/drivers/assign-driver/{driverId}/{carId}",
                        1L, 1L))
                .andExpect(status().isOk())
                .andReturn();

        String expectedBody = "{\"carId\":1,\"brand\":\"Mercedes\",\"model\":\"Sedan\",\"driverId\":1}";
        String actualBody = mvcResult.getResponse().getContentAsString();


        assertEquals(expectedBody, actualBody);


    }






}
