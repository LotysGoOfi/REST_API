package com.example.TZRESTFullAPI.dtos.hotels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {
    private UUID id;
    private String name;
}
