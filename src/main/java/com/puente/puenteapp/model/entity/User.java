package com.puente.puenteapp.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String username;
	
	private String lastName;
	
	private String firstName;
	
	private String email;
	
	private String password;
	
	private String country;
	
	private String phone;
	
	private String foreign;
	
	private String taxCorrelative;
	
	@ManyToOne
    private Role role;

}
