package com.example.TZRESTFullAPI.entities.Embeddables;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Embeddable
public class ArrivalTime {
    private String checkIn;
    private String checkOut;
}
