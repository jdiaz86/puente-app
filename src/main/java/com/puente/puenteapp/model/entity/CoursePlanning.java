package com.puente.puenteapp.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "coursePlanning")
@Data
public class CoursePlanning implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private Date date;
	
	private Integer week;
	
	private Integer assistance;
	
	private String content;
	
	private String learningActivities;
	
	private String evaluationActivities;
	
	@ManyToOne
    private CourseEnrollment CourseEnrollment;
	
	
}
