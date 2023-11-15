package com.example.installmentservice.Services;

import com.example.installmentservice.Entities.InstallmentEntity;
import com.example.installmentservice.Models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;
import com.example.installmentservice.Repositories.InstallmentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class InstallmentService{
    @Autowired
    InstallmentRepository installmentRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<InstallmentEntity> getInstallments(String userRut) {return installmentRepository.findByUserRut(userRut);}

    public InstallmentEntity createInstallment(@ModelAttribute InstallmentEntity installment) {
        installmentRepository.save(installment);
        return installment;
    }


    public Integer getAmountPendingPayment(String userRut){
        String url = "http://score-service/score/discount/{rut}";
        String urlUser = "http://user-service/user/discount/{rut}";
        List<InstallmentEntity> installments = installmentRepository.findUnpaidInstallmentsByUserRut(userRut);
        // We need to make a GET request here
        int discountScore = restTemplate.getForEntity(url, Integer.class, userRut).getBody();
        int userDiscount = restTemplate.getForEntity(urlUser, Integer.class, userRut).getBody();

        int totalAmount = 0;
        for (InstallmentEntity installment : installments) {
            totalAmount += installment.getAmount() *  ((100.0 - userDiscount - discountScore)/100);
        }
        return totalAmount;

    }
    public Integer getAmountPaid(String userRut){
        List<InstallmentEntity> installments = installmentRepository.findPaidInstallmentsByUserRut(userRut);
        int totalAmount = 0;
        for (InstallmentEntity installment : installments) {
            totalAmount += installment.getAmountPaid();
        }
        return totalAmount;
    }
    public Integer getAmountPaidTotal(String userRut){
        return installmentRepository.findPaidInstallmentsByUserRut(userRut).size();
    }

    public LocalDate getLastPayment(String userRut){
        return installmentRepository.findLastPaidDateByUserRut(userRut);
    }
    public Integer getOverdues(String userRut){
        return installmentRepository.findOverdueInstallmentsByUserRut(userRut).size();
    }

    public boolean toggleIsPaidStatus(Integer id) {
        Optional<InstallmentEntity> optionalInstallment = installmentRepository.findById(id);
        if (optionalInstallment.isPresent()) {
            InstallmentEntity installment = optionalInstallment.get();
            installment.setPaid(!installment.isPaid());
            installmentRepository.save(installment);
            return true;
        }
        return false;
    }

}

