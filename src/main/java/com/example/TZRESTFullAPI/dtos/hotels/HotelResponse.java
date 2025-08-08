package com.example.TZRESTFullAPI.dtos.hotels;

import com.example.TZRESTFullAPI.entities.Embeddables.Address;
import com.example.TZRESTFullAPI.entities.Embeddables.ArrivalTime;
import com.example.TZRESTFullAPI.entities.Embeddables.Contacts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {

    private UUID id;

    private String name;

    private String description;
    private String brand;

    private Address address;

    private Contacts contacts;

    private ArrivalTime arrivalTime;

    private List<String> amenities = new ArrayList<>();
}

