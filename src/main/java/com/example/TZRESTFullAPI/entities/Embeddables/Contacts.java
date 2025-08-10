package com.example.TZRESTFullAPI.entities.Embeddables;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {

    private String phone;

    @Email
    private String email;

}
