package com.example.final_exam.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private long id;
    private String name;
    @Column("last_name")
    private String lastName;
    @Column("country_of_origin")
    private Country countryOfOrigin;
    private String email;
}
