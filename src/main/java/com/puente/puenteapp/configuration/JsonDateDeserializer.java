package com.puente.puenteapp.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import static com.puente.puenteapp.util.ConstUtil.JSON_FORMAT_DATE;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JsonDateDeserializer extends JsonDeserializer<Date> {
    
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String date = jp.getText();
        try {
            return JSON_FORMAT_DATE.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
}
