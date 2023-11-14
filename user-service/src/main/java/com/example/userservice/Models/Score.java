package com.example.userservice.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private int score;
    private LocalDate date;
}


