package com.mercadolibre.calculadorametroscuadrados.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mercadolibre.calculadorametroscuadrados.dto.HouseDTO;
import com.mercadolibre.calculadorametroscuadrados.dto.RoomDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CalculateRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testCalculateOK() throws Exception {
        HouseDTO house = new HouseDTO(
                "Casa",
                "Envigado",
                List.of(
                        new RoomDTO("Habitacion1", 10, 5),
                        new RoomDTO("Habitacion2", 20, 10),
                        new RoomDTO("Habitacion3", 40, 25)
                )
        );

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer()
                .withDefaultPrettyPrinter();
        String payloadJson = writer.writeValueAsString(house);

        mockMvc.perform(MockMvcRequestBuilders.post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.squareFeet")
                        .value(1250))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value(1000000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.biggest.name")
                        .value("Habitacion3"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.biggest.width")
                .value(40))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.biggest.length")
                .value(25))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.biggest.squareFeet")
                .value(1000));

    }
}