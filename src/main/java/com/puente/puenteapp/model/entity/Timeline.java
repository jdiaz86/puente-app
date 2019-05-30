package com.puente.puenteapp.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.puente.puenteapp.configuration.JsonDateDeserializer;
import com.puente.puenteapp.configuration.JsonDateSerializer;

import lombok.Data;

@Entity
@Table(name = "timeline")
@Data
public class Timeline implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@JsonSerialize(using=JsonDateSerializer.class)
    @JsonDeserialize(using=JsonDateDeserializer.class)
	private Date date;
	
	private String title;
	
	private String description;
	
	private String icon;
	
	
}
