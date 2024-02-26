package com.mercadolibre.calculadorametroscuadrados.service;

import com.mercadolibre.calculadorametroscuadrados.dto.HouseDTO;
import com.mercadolibre.calculadorametroscuadrados.dto.HouseResponseDTO;
import com.mercadolibre.calculadorametroscuadrados.dto.RoomDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculateServiceTest {

    CalculateService calculateService = new CalculateService();

    @Test
    void testCalculatePriceOK() {
        HouseDTO house = new HouseDTO(
                "Casa",
                "Envigado",
                List.of(
                        new RoomDTO("Habitacion1", 10, 5),
                        new RoomDTO("Habitacion2", 20, 10),
                        new RoomDTO("Habitacion3", 40, 25)
                )
        );

        Integer expectedPrice = 1000000;
        HouseResponseDTO response = calculateService.calculate(house);

        assertEquals(expectedPrice, response.getPrice());
    }

    @Test
    void testCalculateBiggestOK() {
        HouseDTO house = new HouseDTO(
                "Casa",
                "Envigado",
                List.of(
                        new RoomDTO("Habitacion1", 10, 5),
                        new RoomDTO("Habitacion2", 20, 10),
                        new RoomDTO("Habitacion3", 40, 25)
                )
        );

        RoomDTO biggest = house.getRooms().get(2);
        HouseResponseDTO response = calculateService.calculate(house);

        assertEquals(biggest, response.getBiggest());
    }

    @Test
    void testCalculateSquareFeetOK() {
        HouseDTO house = new HouseDTO(
                "Casa",
                "Envigado",
                List.of(
                        new RoomDTO("Habitacion1", 10, 5)
                )
        );

        Integer expectedSquareFeet = 50;
        HouseResponseDTO response = calculateService.calculate(house);

        assertEquals(expectedSquareFeet, response.getSquareFeet());
    }
}