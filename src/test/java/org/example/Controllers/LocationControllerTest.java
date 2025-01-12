package org.example.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTOs.LocationDTO;
import org.example.Repositories.LocationRepository;
import org.example.Services.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void setup(){
        jdbcTemplate.execute("DELETE FROM locations");
        jdbcTemplate.execute("ALTER TABLE locations ALTER COLUMN id RESTART WITH 1");

        locationService.createLocation(new LocationDTO("Meden Kurnik"));
        locationService.createLocation(new LocationDTO("4arodeika"));
    }

    @Test
    void getLocationById_Success() throws Exception {
        mockMvc.perform(get("/location/{locationId}", 2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("4arodeika"));
    }

    //WORK IN PROGRESS

//    @Test
//    void createLocation_Success() throws Exception {
//        var locationDTO = new LocationDTO("Kumluka");
//
//        mockMvc.perform(post("/location")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(locationDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("Kumluka"));
//    }

    @Test
    void updateLocation_Success() throws Exception {
        var locationDTO = new LocationDTO("Izgrew");

        mockMvc.perform(patch("/location/{locationId}", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(locationDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("Izgrew"));
    }

    @Test
    void deleteLocation_Success() {
    }
}