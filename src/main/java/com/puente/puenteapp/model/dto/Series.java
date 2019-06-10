package com.puente.puenteapp.model.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class Series implements Serializable {
    
    String name;
    List<? extends Number> data;
}
