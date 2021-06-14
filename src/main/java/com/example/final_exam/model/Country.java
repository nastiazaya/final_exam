package com.example.final_exam.model;

public enum Country {
    USA("USA"),
    PL("PL"),
    DE("DE");

    private final String country;

    Country(String country) {
        this.country = country;
    }

    @Override
    public String toString(){
        return country;
    }

}
