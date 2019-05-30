package com.puente.puenteapp.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.puente.puenteapp.configuration.JsonDateDeserializer;
import com.puente.puenteapp.configuration.JsonDateSerializer;

import lombok.Data;

@Data
public class DashboardRequest implements Serializable {

	@JsonSerialize(using=JsonDateSerializer.class)
    @JsonDeserialize(using=JsonDateDeserializer.class)
	private Date dateInitial;
	
	@JsonSerialize(using=JsonDateSerializer.class)
    @JsonDeserialize(using=JsonDateDeserializer.class)
	private Date dateFinal;
	
}
