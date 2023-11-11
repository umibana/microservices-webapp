package com.example.installmentservice.Controllers;

import com.example.installmentservice.Entities.InstallmentEntity;
import com.example.installmentservice.Services.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/installment")
public class InstallmentController {
    @Autowired
    InstallmentService installmentService;

    @GetMapping("/hello")
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/{rut}")
    public ResponseEntity<List<InstallmentEntity>> getByRut(@PathVariable("rut") String rut) {
        List<InstallmentEntity> installments = installmentService.getInstallments(rut);
        if (installments == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(installments);

    }

    @PostMapping
    public ResponseEntity<InstallmentEntity> createUser(@RequestBody InstallmentEntity installment) {
        InstallmentEntity newInstallment = installmentService.createInstallment(installment);
        return ResponseEntity.ok(newInstallment);

    }



}
