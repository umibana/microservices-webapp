package com.example.scoreservice.Models;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.UUID;

public class User {
    private UUID id;
    private String rut;
    private String name;
    private String surname;
    private LocalDate birthdate;
    // This is an enum declared in SchoolType.java
    private int graduationYear;
    // This should be separated in it's own entity
    // but due to lack of time we do it here
    private boolean enrollStatus;
    private boolean usingCredit;
    private int discount;
}
