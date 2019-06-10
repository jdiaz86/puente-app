package com.puente.puenteapp.model.repository;

import com.puente.puenteapp.model.entity.CourseEnrollment;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Integer> {
	
	@Query("SELECT AVG(u.studentsAmount) from CourseEnrollment u where u.date BETWEEN ?1 AND ?2")
	Double getAvgAssistance(Date dateInitial, Date dateFinal);
	
	@Query("SELECT COUNT(u.id) from CourseEnrollment u where u.date BETWEEN ?1 AND ?2")
	int getCount(Date dateInitial, Date dateFinal);
	
	@Query(value = "SELECT SUM(u.studentsAmount), u.course.name, u.course.grade FROM CourseEnrollment u where u.date BETWEEN ?1 AND ?2 GROUP BY u.course.name, u.course.grade ORDER BY u.course.name")
	List<Object[]> getCountStudentsByGradeAndTime(Date dateInitial, Date dateFinal);
	

}
