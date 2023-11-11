package  com.example.installmentservice.Repositories;

import com.example.installmentservice.Entities.InstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InstallmentRepository extends JpaRepository<InstallmentEntity, Integer> {


    List<InstallmentEntity> findByUserRut(String userRut);

    @Query("SELECT i FROM InstallmentEntity i WHERE i.isPaid = true AND i.userRut = :userRut")
    List<InstallmentEntity> findPaidInstallmentsByUserRut(@Param("userRut") String rut);

    @Query("SELECT i FROM InstallmentEntity i WHERE i.isPaid = false AND i.userRut = :userRut")
    List<InstallmentEntity> findUnpaidInstallmentsByUserRut(@Param("userRut") String rut);

    @Query("SELECT MAX(i.paidDate) FROM InstallmentEntity i WHERE i.isPaid = true AND i.userRut = :userRut")
    LocalDate findLastPaidDateByUserRut(@Param("userRut") String rut);

    @Query("SELECT i FROM InstallmentEntity i WHERE i.isPaid = false AND i.userRut = :userRut AND i.date < CURRENT_DATE")
    List<InstallmentEntity> findOverdueInstallmentsByUserRut(@Param("userRut") String rut);



}