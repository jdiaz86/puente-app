package com.puente.puenteapp.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class Role implements GrantedAuthority, Serializable  {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String code;
	
	private String name;
	
	private String description;
	
	@Override
	public String getAuthority() {
		return name;
	}
	
	
}
