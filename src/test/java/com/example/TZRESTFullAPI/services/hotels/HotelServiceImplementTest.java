package com.example.TZRESTFullAPI.services.hotels;

import com.example.TZRESTFullAPI.dtos.hotels.HotelRequest;
import com.example.TZRESTFullAPI.dtos.hotels.HotelResponse;
import com.example.TZRESTFullAPI.dtos.hotels.HotelSummaryResponse;
import com.example.TZRESTFullAPI.entities.Hotel;
import com.example.TZRESTFullAPI.repository.HotelsRepository;
import com.example.TZRESTFullAPI.transformers.HotelTransformer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class HotelServiceImplementTest {
    @Mock
    private HotelsRepository hotelsRepository;

    @Mock
    private HotelTransformer hotelTransformer;

    @InjectMocks
    private HotelServiceImplement hotelService;

    private final UUID testId = UUID.randomUUID();
    private final Hotel testHotel = new Hotel();
    private final HotelResponse hotelResponse = new HotelResponse();
    private final HotelSummaryResponse summaryResponse = new HotelSummaryResponse();
    private final HotelRequest hotelRequest = new HotelRequest();

    @Test
    void findById_ExistingId_ShouldReturnHotel() {
        when(hotelsRepository.findById(testId)).thenReturn(Optional.of(testHotel));
        when(hotelTransformer.toHotelResponse(testHotel)).thenReturn(hotelResponse);

        HotelResponse result = hotelService.findById(testId);

        assertNotNull(result);
        assertEquals(hotelResponse, result);
        verify(hotelsRepository).findById(testId);
        verify(hotelTransformer).toHotelResponse(testHotel);
    }

    @Test
    void findById_NotExistingId_ShouldThrowException() {
        when(hotelsRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> hotelService.findById(testId));
        verify(hotelsRepository).findById(testId);
        verify(hotelTransformer, never()).toHotelResponse(any());
    }

    @Test
    void create_ValidRequest_ShouldReturnSavedHotel() {
        when(hotelTransformer.toHotel(hotelRequest)).thenReturn(testHotel);
        when(hotelsRepository.save(testHotel)).thenReturn(testHotel);
        when(hotelTransformer.toHotelSummaryResponse(testHotel)).thenReturn(summaryResponse);

        HotelSummaryResponse result = hotelService.create(hotelRequest);

        assertNotNull(result);
        assertEquals(summaryResponse, result);
        verify(hotelTransformer).toHotel(hotelRequest);
        verify(hotelsRepository).save(testHotel);
        verify(hotelTransformer).toHotelSummaryResponse(testHotel);
    }

    @Test
    @Transactional
    void addAmenities_NewAmenities_ShouldAddThem() {
        List<String> existingAmenities = List.of("WiFi");
        List<String> newAmenities = List.of("Pool", "Gym");
        List<String> expectedAmenities = List.of("WiFi", "Pool", "Gym");

        testHotel.setAmenities(new ArrayList<>(existingAmenities));
        when(hotelsRepository.findById(testId)).thenReturn(Optional.of(testHotel));
        when(hotelsRepository.save(testHotel)).thenReturn(testHotel);
        when(hotelTransformer.toHotelResponse(testHotel)).thenReturn(hotelResponse);

        HotelResponse result = hotelService.addAmenities(testId, newAmenities);

        assertNotNull(result);
        assertEquals(hotelResponse, result);
        assertEquals(3, testHotel.getAmenities().size());
        assertTrue(testHotel.getAmenities().containsAll(expectedAmenities));
        verify(hotelsRepository).findById(testId);
        verify(hotelsRepository).save(testHotel);
    }

    @Test
    void addAmenities_NotExistingHotel_ShouldThrowException() {
        when(hotelsRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> hotelService.addAmenities(testId, List.of("Pool")));
        verify(hotelsRepository).findById(testId);
        verify(hotelsRepository, never()).save(any());
    }

    @Test
    void searchHotels_WithAllParams_ShouldCallRepository() {
        List<Hotel> hotels = List.of(new Hotel(), new Hotel());
        when(hotelsRepository.searchHotels("name", "brand", "city", "country", "amenity"))
                .thenReturn(hotels);
        when(hotelTransformer.toListHotelSummaryResponse(hotels))
                .thenReturn(List.of(summaryResponse, summaryResponse));

        List<HotelSummaryResponse> result = hotelService.searchHotels(
                "name", "brand", "city", "country", "amenity");

        assertEquals(2, result.size());
        verify(hotelsRepository).searchHotels("name", "brand", "city", "country", "amenity");
    }

    @Test
    void searchHotels_WithNullParams_ShouldHandleCorrectly() {
        List<Hotel> hotels = List.of(new Hotel());
        when(hotelsRepository.searchHotels(null, null, null, null, null))
                .thenReturn(hotels);
        when(hotelTransformer.toListHotelSummaryResponse(hotels))
                .thenReturn(List.of(summaryResponse));

        List<HotelSummaryResponse> result = hotelService.searchHotels(null, null, null, null, null);

        assertEquals(1, result.size());
    }
}