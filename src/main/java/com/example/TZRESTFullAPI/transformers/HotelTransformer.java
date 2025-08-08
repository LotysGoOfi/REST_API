package com.example.TZRESTFullAPI.transformers;

import com.example.TZRESTFullAPI.dtos.hotels.HotelRequest;
import com.example.TZRESTFullAPI.dtos.hotels.HotelResponse;
import com.example.TZRESTFullAPI.dtos.hotels.HotelSummaryResponse;
import com.example.TZRESTFullAPI.entities.Embeddables.Address;
import com.example.TZRESTFullAPI.entities.Hotel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HotelTransformer {

    public HotelResponse toHotelResponse(Hotel hotel) {
        return HotelResponse
                .builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .brand(hotel.getBrand())
                .address(hotel.getAddress())
                .arrivalTime(hotel.getArrivalTime())
                .contacts(hotel.getContacts())
                .amenities(hotel.getAmenities())
                .build();
    }

    public HotelSummaryResponse toHotelSummaryResponse(Hotel hotel) {
        return HotelSummaryResponse
                .builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(formatAddress(hotel.getAddress()))
                .phone(hotel.getContacts().getPhone())
                .build();
    }
    public Hotel toHotel(HotelRequest hotelRequest) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelRequest.getName());
        hotel.setAddress(hotelRequest.getAddress());
        hotel.setContacts(hotelRequest.getContacts());
        hotel.setArrivalTime(hotelRequest.getArrivalTime());
        hotel.setBrand(hotelRequest.getBrand());
        hotel.setDescription(hotelRequest.getDescription());
        return hotel;
    }

    public List<HotelSummaryResponse> toListHotelSummaryResponse(List<Hotel> hotels) {
        List<HotelSummaryResponse> hotelResponses  = new ArrayList<>();
        hotels.forEach(hotel -> hotelResponses.add(toHotelSummaryResponse(hotel)));
        return hotelResponses;
    }

    private String formatAddress(Address address) {
        return String.format("%d %s, %s, %s %s",
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostCode());
    }
}
