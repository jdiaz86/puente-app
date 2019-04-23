package com.puente.puenteapp.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "course")
@Data
public class Course implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String code;
	
	private String name;
	
	private Integer grade;
	
	private String description;
	
	
}
