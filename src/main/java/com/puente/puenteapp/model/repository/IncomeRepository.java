package com.puente.puenteapp.model.repository;

import com.puente.puenteapp.model.entity.Income;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {

    @Query("SELECT SUM(u.amount) from Income u where u.date BETWEEN ?1 AND ?2")
    Double getSumAmount(Date dateInitial, Date dateFinal);
	
    @Query(value = "SELECT SUM(u.amount), extract(month from u.date) as month FROM income u where u.date BETWEEN ?1 AND ?2 GROUP BY month", nativeQuery = true)
    List<Object[]> getSumAmountByMonth(Date dateInitial, Date dateFinal);
}
