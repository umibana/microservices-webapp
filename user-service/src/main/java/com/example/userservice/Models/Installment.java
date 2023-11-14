package com.example.userservice.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private boolean isPaid;
    private int amount;
    private double amountPaid;
    private LocalDate date;
    private LocalDate paidDate;
    private String userRut;
}
