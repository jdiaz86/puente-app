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
@Table(name = "CourseEnrollment")
@Data
public class CourseEnrollment implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private Date year;
	
	private Integer studentsAmount;
	
	private String description;
	
	@ManyToOne
    private User user;
	
	@ManyToOne
    private Course Course;
	
	
}
