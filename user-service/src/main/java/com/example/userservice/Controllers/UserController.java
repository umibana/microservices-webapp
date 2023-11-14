package com.example.userservice.Controllers;


import com.example.userservice.Entities.UserEntity;
import com.example.userservice.Models.Installment;
import jakarta.ws.rs.core.Response;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.userservice.Services.UserService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/hello")
    public ResponseEntity<String> getHelloWorld(){
        return ResponseEntity.ok("Hello World");
    }


    @GetMapping("/{rut}")
    public ResponseEntity<UserEntity> getByRut(@PathVariable("rut") String rut){
        UserEntity user = userService.findByRut(rut);
        if (user == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/discount/{rut}")
    public ResponseEntity<Integer> getUserDiscount(@PathVariable("rut") String rut){
        UserEntity user = userService.findByRut(rut);
        if (user == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getDiscount());
    }

    @PostMapping
    public ResponseEntity<?> postUser(@ModelAttribute UserEntity user) {
        userService.createUser(user);
        return ResponseEntity.ok(user);
    }




}
