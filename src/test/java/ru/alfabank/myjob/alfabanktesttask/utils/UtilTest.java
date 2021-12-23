package ru.alfabank.myjob.alfabanktesttask.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.alfabank.myjob.alfabanktesttask.model.ExchangeRate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilTest {

    @Test
    void getExchangeRateURI()  {
        URI uri_test = Util.getExchangeRateURI("https://openexchangerates.org/api/", "565f73b336d645609ef7428e27f4ee4e", "USD", LocalDateTime.now());
        URI uri = URI.create("https://openexchangerates.org/api/latest.json?app_id="+"565f73b336d645609ef7428e27f4ee4e"+"&base=USD");
        Assertions.assertTrue(uri.equals(uri_test));
    }

    @Test
    void getGifURI() {
        URI uri_test = Util.getGifURI("https://api.giphy.com/", "8QKNZYCbfLqRSvSkRwqh4GGKujvFKcLk", "rich");
        URI uri = URI.create("https://api.giphy.com/v1/gifs/random?api_key=8QKNZYCbfLqRSvSkRwqh4GGKujvFKcLk&tag=rich");
        Assertions.assertTrue(uri.equals(uri_test));
    }

    @Test
    void compareExchangeRate()  {
        Map<String, Double> exMap = new HashMap<>();
        exMap.put("RUB", 75.21);
        ExchangeRate exchangeRate = new ExchangeRate("disclaimer", "license", "USD", exMap);
        Map<String, Double> exMap1 = new HashMap<>();
        exMap1.put("RUB", 73.36);
        ExchangeRate exchangeRate1 = new ExchangeRate("disclaimer", "license", "USD", exMap1);
        Assertions.assertTrue(Util.compareExchangeRate(exchangeRate, exchangeRate1, "RUB"));
    }

    @Test
    void getCodesUtils() {

        Map<String, Double> exMap = new HashMap<>();
        exMap.put("RUB", 73.36);
        ExchangeRate exchangeRate = new ExchangeRate("disclaimer", "license", "USD", exMap);

        Assertions.assertNotNull(Util.getCodesUtils(exchangeRate));
    }
}
