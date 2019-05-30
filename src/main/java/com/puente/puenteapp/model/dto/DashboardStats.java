package com.puente.puenteapp.model.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DashboardStats implements Serializable {

	private Integer totalDonors;
	private Integer totalTeachers;
	private Integer natlDonors;
	private Integer intlDonors;
	private Double totalIncomes;
	private Double totalOutcomes;
	private Integer totalAsignedCourses;
	private Double avgStudents;
	List<Series> incomesVsOutcomesByMonth;
	List<Series> outcomeByTypeAndMonth;
	List<Series> coursesByGradeAndTime;
	
}
