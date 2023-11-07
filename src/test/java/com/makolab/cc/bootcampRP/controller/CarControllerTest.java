package com.makolab.cc.bootcampRP.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makolab.cc.bootcampRP.dto.CarDto;
import com.makolab.cc.bootcampRP.mapper.CarMapper;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.User;
import com.makolab.cc.bootcampRP.service.CarService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Slf4j
@TestPropertySource(locations = "classpath:tests.properties")
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarMapper carMapper;

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
        driver1.setName("Michał");
        driver1.setCar(car1);

        car.setDriver(driver);
        car1.setDriver(driver1);


        List<Car> carList = new ArrayList<>();
        carList.add(car);
        carList.add(car1);

        when(carService.findAll()).thenReturn(carList);

        MvcResult mvcResult = mockMvc
                .perform(get("/cars"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "[{\"carId\":1,\"brand\":\"Mercedes\",\"model\":\"Sedan\",\"driverId\":1}," +
                "{\"carId\":2,\"brand\":\"Fiat\",\"model\":\"125p\",\"driverId\":2}]";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);

    }

    @Test
    public void findAllByBrandShouldReturnOK() throws Exception {
        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");


        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");
        driver.setCar(car);

        car.setDriver(driver);

        List<Car> carList = new ArrayList<>();
        carList.add(car);


        when(carService.findAllByBrand("Mercedes")).thenReturn(carList);

        MvcResult mvcResult = mockMvc
                .perform(get("/cars/by-brand/{brand}",  "Mercedes"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "[{\"carId\":1,\"brand\":\"Mercedes\",\"model\":\"Sedan\",\"driverId\":1}]";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);

    }

    @Test
    public void findAllByModelShouldReturnOK() throws Exception {
        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");


        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");
        driver.setCar(car);

        car.setDriver(driver);

        List<Car> carList = new ArrayList<>();
        carList.add(car);


        when(carService.findAllByModel("Sedan")).thenReturn(carList);

        MvcResult mvcResult = mockMvc
                .perform(get("/cars/by-model/{model}",  "Sedan"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "[{\"carId\":1,\"brand\":\"Mercedes\",\"model\":\"Sedan\",\"driverId\":1}]";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);

    }

    @Test
    public void findAllByCarStatusShouldReturnOK() throws Exception {
        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        car.setCarStatus(CarStatus.DRIVING);


        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");
        driver.setCar(car);

        Car car1 = new Car();
        car1.setCarId(2L);
        car1.setBrand("Fiat");
        car1.setCarStatus(CarStatus.DRIVING);
        car1.setModel("125p");


        Driver driver1 = new Driver();
        driver1.setDriverId(2L);
        driver1.setName("Michał");
        driver1.setCar(car1);

        car.setDriver(driver);
        car1.setDriver(driver1);


        List<Car> carList = new ArrayList<>();
        carList.add(car);
        carList.add(car1);


        when(carService.findAllByCarStatus(CarStatus.DRIVING)).thenReturn(carList);

        MvcResult mvcResult = mockMvc
                .perform(get("/cars/by-status/{carStatus}",  "DRIVING"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "[{\"carId\":1,\"brand\":\"Mercedes\",\"model\":\"Sedan\",\"driverId\":1,\"carStatus\":\"DRIVING\"}," +
                "{\"carId\":2,\"brand\":\"Fiat\",\"model\":\"125p\",\"driverId\":2,\"carStatus\":\"DRIVING\"}]";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);

    }

    @Test
    public void countCarsShouldReturnOK() throws Exception {


        when(carService.calculateNumberOfCars()).thenReturn(2);

        MvcResult mvcResult = mockMvc
                .perform(get("/cars/count"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "2";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);

    }

    @Test
    public void totalDistanceShouldReturnOK() throws Exception {


        when(carService.calculateTotalDistanceOfCars()).thenReturn(150.0);

        MvcResult mvcResult = mockMvc
                .perform(get("/cars/totaldistance"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "150.0";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);

    }

    @Test
    void addCarShouldReturnOK() throws Exception {
        CarDto dto = new CarDto();
        dto.setBrand("Mercedes");
        dto.setModel("Sedan");

        CarDto returnDto = new CarDto();
        returnDto.setCarId(1L);
        returnDto.setBrand("Mercedes");
        returnDto.setModel("Sedan");

        when(carService.addNewCar(any(Car.class)))
                .thenReturn(carMapper.addCar_DtoToCar(returnDto));


        MvcResult mvcResult = mockMvc
                .perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn();

        String expectedBody = "{\"carId\":1,\"brand\":\"Mercedes\",\"model\":\"Sedan\"}";
        String actualBody = mvcResult.getResponse().getContentAsString();


        assertEquals(expectedBody, actualBody);

    }



}
