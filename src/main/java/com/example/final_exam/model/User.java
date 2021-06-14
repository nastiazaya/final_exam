package com.example.final_exam.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private long id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "country_of_origin")
    private Country countryOfOrigin;
    private String email;
}
