package com.example.final_exam.model.jpa;

import lombok.Data;

import javax.persistence.*;

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
    private String countryOfOrigin;
    private String email;
}
