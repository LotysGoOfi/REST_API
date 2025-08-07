package com.example.TZRESTFullAPI.transformers;

import com.example.TZRESTFullAPI.dtos.hotels.HotelSummaryResponse;
import com.example.TZRESTFullAPI.entities.Hotel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HotelTransformer {

    public HotelSummaryResponse transform(Hotel hotel) {
        return HotelSummaryResponse
                .builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .build();
    }
    public List<HotelSummaryResponse> transform(List<Hotel> hotels) {
        List<HotelSummaryResponse> hotelResponses  = new ArrayList<>();
        hotels.forEach(hotel -> hotelResponses.add(transform(hotel)));
        return hotelResponses;
    }
}
