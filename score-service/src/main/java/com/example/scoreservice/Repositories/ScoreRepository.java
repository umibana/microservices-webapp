package com.example.scoreservice.Repositories;

import com.example.scoreservice.Entities.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface ScoreRepository extends JpaRepository<ScoreEntity, Integer> {

    List<ScoreEntity> findByUserRut(String userRut);

    ScoreEntity findByUserRutAndDate(String userRut, LocalDate date);

}