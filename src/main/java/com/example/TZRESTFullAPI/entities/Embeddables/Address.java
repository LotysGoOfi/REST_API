package com.example.TZRESTFullAPI.entities.Embeddables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private int houseNumber;
    private String street;
    private String city;
    private String country;
    private int postCode;
}
