package com.example.TZRESTFullAPI.entities;

import com.example.TZRESTFullAPI.entities.Embeddables.Address;
import com.example.TZRESTFullAPI.entities.Embeddables.ArrivalTime;
import com.example.TZRESTFullAPI.entities.Embeddables.Contacts;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String description;

    private String brand;

    private Address address;

    private Contacts contacts;

    private ArrivalTime arrivalTime;

    @ElementCollection
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    private List<String> amenities = new ArrayList<>();

}
