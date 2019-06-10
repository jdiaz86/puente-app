/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puente.puenteapp.view;

import static com.puente.puenteapp.util.ConstUtil.JSON_FORMAT_DATE;
import java.text.ParseException;
import java.util.Date;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

/**
 *
 * @author jdiaz
 */
@Component
public abstract class AbstractExportView extends AbstractView {
    
    protected String username;
    protected String date;
    protected String time;
    protected String sponsorTitle;
    protected String[] tableTitles;
    protected String[][] tableBody;
    protected String screenTitle;
    protected static final String USER_NAME = "username";
    protected static final String CLIENT_TIME = "time";
    protected static final String ANONYMOUS_USER = "Anonymous";
    protected static final String NO_DATA_FOUND = "No data found";
    protected static final String SPONSOR = "sponsor";    
    
    protected Date isDate(String value) {
        try {
            return JSON_FORMAT_DATE.parse(value);
        } catch (ParseException ex) {
            return null;
        }
    }
    
    protected Double isDouble(String value) {
        try {
            return new Double(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public void setScreenTitle(String screenTitle) {
        this.screenTitle = screenTitle;
    }
    
    public static Number addNumbers(Number a, Number b) {
        if(a instanceof Double || b instanceof Double) {
            return a.doubleValue() + b.doubleValue();
        } else if(a instanceof Float || b instanceof Float) {
            return a.floatValue() + b.floatValue();
        } else if(a instanceof Long || b instanceof Long) {
            return a.longValue() + b.longValue();
        } else {
            return a.intValue() + b.intValue();
        }
    }

    
}
