package com.example.installmentservice.Controllers;

import com.example.installmentservice.Entities.InstallmentEntity;
import com.example.installmentservice.Services.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
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
    @PostMapping("/updatePaid/{id}")
    public ResponseEntity<Void> updatePaid(@PathVariable Integer id) {
        boolean updated = installmentService.toggleIsPaidStatus(id);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @PostMapping
    public ResponseEntity<InstallmentEntity> createUser(@RequestBody InstallmentEntity installment) {
        InstallmentEntity newInstallment = installmentService.createInstallment(installment);
        return ResponseEntity.ok(newInstallment);

    }
    @GetMapping("/unpaid/{rut}")
    public ResponseEntity<Integer> getUnpaid(@PathVariable("rut") String rut){
        return ResponseEntity.ok(installmentService.getAmountPendingPayment(rut));

    }
    @GetMapping("/paid/{rut}")
    public ResponseEntity<Integer> getPaid(@PathVariable("rut") String rut){
        return ResponseEntity.ok(installmentService.getAmountPaid(rut));

    }

    @GetMapping("/paidtotal/{rut}")
    public ResponseEntity<Integer> getPaidTotal(@PathVariable("rut") String rut){
        return ResponseEntity.ok(installmentService.getAmountPaidTotal(rut));

    }

    @GetMapping("/lastpaid/{rut}")
    public ResponseEntity<LocalDate> getLastPayment(@PathVariable("rut") String rut){
        return ResponseEntity.ok(installmentService.getLastPayment(rut));

    }
    @GetMapping("/overdues/{rut}")
    public ResponseEntity<Integer> getOverdues(@PathVariable("rut") String rut){
        return ResponseEntity.ok(installmentService.getOverdues(rut));

    }






}
