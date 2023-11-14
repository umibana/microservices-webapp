package com.example.userservice.Services;
import com.example.userservice.Models.Installment;
import com.example.userservice.Models.SchoolType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;
import com.example.userservice.Repositories.UserRepository;
import com.example.userservice.Entities.UserEntity;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;


    int enrollment = 70000;
    int tuition = 1500000;


    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public UserEntity findByRut(String rut) {return userRepository.findByRut(rut);}

    public void createUser(@ModelAttribute UserEntity user) {
        // Assuming there's an endpoint to get user discounts and available installments
        String installmentsUrl = "http://payment-service/installments/";

        Integer discount = getUserDiscounts(user);
        Integer availableInstallments = getAvailableInstallments(user);
        user.setDiscount(discount);

        LocalDate nextMonth = LocalDate.now().plus(1, ChronoUnit.MONTHS).withDayOfMonth(5);


        // For each one, we send a POST request creating the installment
        for (int i = 0; i < availableInstallments; i++) {
            LocalDate installmentDate = nextMonth.plus(i, ChronoUnit.MONTHS);
            Installment installment = new Installment();
            installment.setDate(installmentDate);
            installment.setAmount(tuition / availableInstallments);
            installment.setUserRut(user.getRut());
            restTemplate.postForEntity(installmentsUrl, installment, Installment.class);
        }

        userRepository.save(user);
    }

    // Returns discount according to schoolType and years
    // since graduation
    public Integer getUserDiscounts(UserEntity user) {
        int currentDiscount = 0;
        int yearsSinceGraduation = Year.now().getValue() - user.getGraduationYear();
        SchoolType schoolType = user.getSchoolType();
        // If it isn't any of these then it's a private school
        // and no discount applies
        if (schoolType == SchoolType.MUNICIPAL) {
            currentDiscount += 20;
        } else if (schoolType == SchoolType.SUBVENCIONADO) {
            currentDiscount += 10;
        }
        if (yearsSinceGraduation < 1) {
            currentDiscount += 15;
        } else if (yearsSinceGraduation <= 2) {
            currentDiscount += 8;
        } else if (yearsSinceGraduation <= 4) {
            currentDiscount += 4;
        }
        return currentDiscount;
    }

    public Integer getAvailableInstallments(UserEntity user) {
        int installments = 0;
        SchoolType schoolType = user.getSchoolType();
        if (schoolType == SchoolType.MUNICIPAL) {
            installments = 10;
        } else if (schoolType == SchoolType.SUBVENCIONADO) {
            installments = 7;
        } else if (schoolType == SchoolType.PRIVADO) {
            installments = 4;
        }
        return installments;
    }



    public List<Installment> findInstallments(String rut) {
        String url = "http://installment-service/installment/{rut}";
        ResponseEntity<List<Installment>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Installment>>() {},
                rut
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            // If there are no installments
            return Collections.emptyList();
        }
    }

    public String getUserSummary(String rut) {
        String getInstallmentUrl = "http://installment-service/installment/{rut}";
        String lastPaymentUrl = "http://installment-service/lastpaid/{rut}";
        String amountPaidUrl = "http://installment-service/paid/{rut}";
        String amountUnpaidUrl = "http://installment-service/unpaid/{rut}";
        String amountPaidTotalUrl = "http://installment-service/paidtotal/{rut}";
        String amountOverdueUrl = "http://installment-service/overdues/{rut}";
        String examsTaken = "http://score-service/exams/{rut}";
        String averageScore = "http://score-service/average/{rut}";


        UserEntity user = findByRut(rut);
        LocalDate lastPayment = restTemplate.getForEntity(lastPaymentUrl, LocalDate.class, rut).getBody();

        DateTimeFormatter formatter = null;
        if (lastPayment != null) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        }

        Integer amountPaid = restTemplate.getForEntity(amountPaidUrl,Integer.class,rut).getBody();
        Integer amountUnpaid = restTemplate.getForEntity(amountUnpaidUrl, Integer.class,rut).getBody();


        String summary = "";
        summary += "RUT: " + user.getRut() + "\n";
        summary += "Nombre: " + user.getName() + " " + user.getSurname() + "\n";
        summary += "Examenes rendidos: " + restTemplate.getForEntity(examsTaken, Integer.class,rut).getBody() + "\n";
        summary += "Promedio: " + restTemplate.getForEntity(averageScore, Integer.class,rut).getBody() + "\n";
        summary += "Monto total arancel a pagar" + (amountPaid + amountUnpaid) + "\n";
        summary += "Tipo de pago: " + (user.isUsingCredit() ? "Crédito" : "Contado") + "\n";
        summary += "Cuotas pactadas: " + (restTemplate.getForEntity(getInstallmentUrl, List.class, rut).getBody()).size() + "\n";
        summary += "Cuotas pagadas: " + restTemplate.getForEntity(amountPaidTotalUrl, Integer.class,rut).getBody() + "\n";
        summary += "Monto total pagado: " + amountPaid + "\n";
        summary += "Fecha ultimo pago: " + (lastPayment != null ? lastPayment.format(formatter) : "No pagada") + "\n";
        summary += "Saldo por pagar: " + amountUnpaid + "\n";
        summary += "Nro. Cuotas con retraso" + restTemplate.getForEntity(amountOverdueUrl,Integer.class,rut).getBody() + "\n";

        return summary;


    }



}