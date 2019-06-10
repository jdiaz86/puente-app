package com.puente.puenteapp.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class ConstUtil {

    public static final String DATE_FORMAT = "dd-MMM-yyyy";
    public static final SimpleDateFormat JSON_FORMAT_DATE = new SimpleDateFormat(DATE_FORMAT, Locale.US);
    public static final String EMPTY_STRING = "";
    
}
