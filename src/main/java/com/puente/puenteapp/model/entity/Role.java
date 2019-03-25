package com.puente.puenteapp.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class Role implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@NotBlank(message = "validation.notNull.code")
	@Column(name = "code")
    private String code;
	
	@NotBlank(message = "validation.notNull.name")
	@Column(name = "name")
    private String name;
	
	@Column(name = "description")
	private String description;
	
	
}
