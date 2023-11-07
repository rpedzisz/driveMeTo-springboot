package com.makolab.cc.bootcampRP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makolab.cc.bootcampRP.mapper.CarMapper;
import com.makolab.cc.bootcampRP.mapper.CourseMapper;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.service.CarService;
import com.makolab.cc.bootcampRP.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Slf4j
@TestPropertySource(locations = "classpath:tests.properties")
public class CourseControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Test
    void totalFeeShouldReturnOK() throws Exception {

        when(courseService.calculateTotalFee()).thenReturn(100.0);

        MvcResult mvcResult = mockMvc
                .perform(get("/courses/totalfee"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "100.0";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);
    }

}
