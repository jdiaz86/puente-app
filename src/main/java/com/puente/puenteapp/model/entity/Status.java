package com.puente.puenteapp.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "status")
@Data
public class Status implements Serializable {
	
	public static Status ACTIVE = new Status(1);
    public static Status INACTIVE = new Status(2);
    public static Status INVITED = new Status(3);
    public static Status DELETED = new Status(4);
    
    public Status() {
	}
    
    public Status(Integer id) {
    	this.id = id;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String code;
	
	private String name;
	
	private String description;
	
	
}
