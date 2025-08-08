package com.example.TZRESTFullAPI.controllers;

import com.example.TZRESTFullAPI.dtos.hotels.HotelRequest;
import com.example.TZRESTFullAPI.dtos.hotels.HotelResponse;
import com.example.TZRESTFullAPI.dtos.hotels.HotelSummaryResponse;

import com.example.TZRESTFullAPI.services.hotels.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/property-view")
public class HostelController {

    private final HotelService hotelService;

    @GetMapping("/hotels")
    @ResponseStatus(value = HttpStatus.OK)
    public List<HotelSummaryResponse> findByAll(){
        return hotelService.findByAllSummary();
    }

    @GetMapping("/hostels/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public HotelResponse findById(@PathVariable UUID id){
        return hotelService.findById(id);
    }

    @PostMapping("/hotels")
    @ResponseStatus(value = HttpStatus.OK)
    public HotelSummaryResponse createHotel(@RequestBody HotelRequest hotelRequest){
        return hotelService.create(hotelRequest);
    }
}
