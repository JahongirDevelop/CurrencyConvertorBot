package uz.pdp.currencyconvertor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.pdp.currencyconvertor.model.Currency;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CurrencyService {

    public String convert(String code, boolean toUZS, Double amount) {
        Currency currency = getCurrency(code);

        if (toUZS) {
            return amount * currency.getRate() + "\n Rate: " + currency.getRate();
        }
        return amount / currency.getRate() + "\n Rate: " + currency.getRate();
    }

    public Currency getCurrency(String code) {
        try {
            URL url = new URL("https://cbu.uz/uz/arkhiv-kursov-valyut/json/" + code + "/");
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(url, new TypeReference<ArrayList<Currency>>() {
            }).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        }
