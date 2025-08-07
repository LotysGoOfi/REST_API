package com.example.TZRESTFullAPI.entities.Embeddables;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {
    private int houseNumber;
    private String street;
    private String city;
    private String country;
    private int postCode;
}
