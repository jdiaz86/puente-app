package com.puente.puenteapp.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import static com.puente.puenteapp.util.ConstUtil.JSON_FORMAT_DATE;
import java.io.IOException;
import java.util.Date;
import org.springframework.stereotype.Component;


@Component
public class JsonDateSerializer extends JsonSerializer<Date>{

    @Override
    public void serialize(Date date, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        String formattedDate = JSON_FORMAT_DATE.format(date);
        jgen.writeString(formattedDate);
    }
    
}
