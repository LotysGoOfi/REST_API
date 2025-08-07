package com.example.TZRESTFullAPI.entities.Embeddables;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Embeddable
public class ArrivalTime {
    private Time checkIn;
    private Time checkOut;

}
