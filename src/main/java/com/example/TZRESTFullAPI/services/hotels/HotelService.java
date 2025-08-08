package com.example.TZRESTFullAPI.services.hotels;

import com.example.TZRESTFullAPI.dtos.hotels.HotelRequest;
import com.example.TZRESTFullAPI.dtos.hotels.HotelResponse;
import com.example.TZRESTFullAPI.dtos.hotels.HotelSummaryResponse;

import java.util.List;
import java.util.UUID;


public interface HotelService {
    HotelResponse findById(UUID uuid);
    List<HotelSummaryResponse> findByAllSummary();
    HotelSummaryResponse create(HotelRequest request);
}
