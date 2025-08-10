package com.example.TZRESTFullAPI.controllers;


import com.example.TZRESTFullAPI.dtos.hotels.HotelResponse;
import com.example.TZRESTFullAPI.dtos.hotels.HotelSummaryResponse;
import com.example.TZRESTFullAPI.entities.Embeddables.Address;
import com.example.TZRESTFullAPI.entities.Embeddables.ArrivalTime;
import com.example.TZRESTFullAPI.entities.Embeddables.Contacts;
import com.example.TZRESTFullAPI.services.hotels.HotelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HostelController.class)
class HostelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private HotelService hotelService;

    private final UUID testId = UUID.randomUUID();
    private final HotelSummaryResponse summaryResponse = new HotelSummaryResponse(
            testId, "Test Hotel", "Description", "Test Address", "+1234567890");

    private final HotelResponse fullResponse = new HotelResponse(
            testId, "Test Hotel", "Description", "Hilton",
            new Address(1, "Street", "Minsk", "Belarus", 220000),
            new Contacts("+1234567890", "test@test.com"),
            new ArrivalTime("14:00", "12:00"),
            List.of("WiFi", "Pool"));

    @Test
    void findAllHotels_ShouldReturnList() throws Exception {
        when(hotelService.findByAllSummary()).thenReturn(List.of(summaryResponse));

        mockMvc.perform(get("/property-view/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(testId.toString()))
                .andExpect(jsonPath("$[0].name").value("Test Hotel"));

        verify(hotelService).findByAllSummary();
    }

    @Test
    void findHotelById_ShouldReturnHotel() throws Exception {
        when(hotelService.findById(testId)).thenReturn(fullResponse);

        mockMvc.perform(get("/property-view/hostels/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.brand").value("Hilton"))
                .andExpect(jsonPath("$.amenities", hasSize(2)));

        verify(hotelService).findById(testId);
    }

    @Test
    void findHotelById_NotFound_ShouldReturn404() throws Exception {
        when(hotelService.findById(testId)).thenThrow(new NoSuchElementException("Hotel not found"));

        mockMvc.perform(get("/property-view/hostels/{id}", testId))
                .andExpect(status().isNotFound());

        verify(hotelService).findById(testId);
    }

    @Test
    void searchHotels_WithParams_ShouldReturnFiltered() throws Exception {
        when(hotelService.searchHotels("test", null, null, null, "WiFi"))
                .thenReturn(List.of(summaryResponse));

        mockMvc.perform(get("/property-view/search")
                        .param("name", "test")
                        .param("amenity", "WiFi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Hotel"));

        verify(hotelService).searchHotels("test", null, null, null, "WiFi");
    }


}