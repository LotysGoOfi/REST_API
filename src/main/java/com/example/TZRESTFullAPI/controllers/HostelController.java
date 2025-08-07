package com.example.TZRESTFullAPI.controllers;

import com.example.TZRESTFullAPI.dtos.hotels.HotelResponse;
import com.example.TZRESTFullAPI.dtos.hotels.HotelSummaryResponse;

import com.example.TZRESTFullAPI.services.hotels.HotelService;
import com.example.TZRESTFullAPI.transformers.HotelTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/property-view")
public class HostelController {

    private final HotelService hotelService;
    private final HotelTransformer hotelTransformer;

    @GetMapping("/hotels")
    @ResponseStatus(value = HttpStatus.OK)
    public List<HotelSummaryResponse> findByAll(){
        return hotelTransformer.transform(
                hotelService.findByAll());
    }

    @GetMapping("hostels/id={id}")
    @ResponseStatus(value = HttpStatus.OK)
    public HotelResponse findById(@PathVariable UUID id){
        return null;
    }
}
