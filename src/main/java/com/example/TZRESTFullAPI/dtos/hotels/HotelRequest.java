package com.example.TZRESTFullAPI.dtos.hotels;

import com.example.TZRESTFullAPI.entities.Embeddables.Address;
import com.example.TZRESTFullAPI.entities.Embeddables.ArrivalTime;
import com.example.TZRESTFullAPI.entities.Embeddables.Contacts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {

    private String name;

    private String description;

    private String brand;

    private Address address;

    private Contacts contacts;

    private ArrivalTime arrivalTime;

}
