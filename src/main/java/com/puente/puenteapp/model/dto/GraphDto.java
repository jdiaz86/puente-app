/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puente.puenteapp.model.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 *
 * @author jdiaz
 */
@Data
public class GraphDto implements Serializable {
        
    String time;
    String title;
    String username;
    String sponsor;
    List<Series> series;
    List<String> cols;
    
}
