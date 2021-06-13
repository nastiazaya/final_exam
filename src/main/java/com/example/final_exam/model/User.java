package com.example.final_exam.model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class User {

    @Id
    private long id;
    private String name;
    private String lastName;
    private Country countryOfOrigin;
    private String email;

}
