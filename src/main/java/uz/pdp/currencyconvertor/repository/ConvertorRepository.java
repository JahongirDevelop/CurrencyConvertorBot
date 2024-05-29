package uz.pdp.currencyconvertor.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import uz.pdp.currencyconvertor.model.Exchange;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConvertorRepository {

    static String convertorPath = "YOUR_LOCAL_STORAGE/src/main/resources/convertor.json"; // Relative path
    static ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
            .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);

    public ArrayList<Exchange> convertorReadFromFile() {
        try {
            return objectMapper.readValue(new File(convertorPath), new TypeReference<ArrayList<Exchange>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void convertorWriteToFile(ArrayList<Exchange> currencies) {
        try {
            objectMapper.writeValue(new File(convertorPath), currencies);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
