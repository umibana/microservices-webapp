package com.example.userservice.Repositories;

import com.example.userservice.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByRut(String rut);

}