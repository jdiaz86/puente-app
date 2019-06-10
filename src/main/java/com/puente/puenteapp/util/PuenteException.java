package com.puente.puenteapp.util;

import java.util.Collections;
import java.util.List;

public class PuenteException extends Exception {
    
    
    private static final long serialVersionUID = 3960043643445556776L;
    private List<String> errors;
    
    public PuenteException(List<String> errors) {
        super("Something wrong has happened");
        this.errors = errors;
    }
    public PuenteException(String error) {
        this(Collections.singletonList(error));
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    
}
