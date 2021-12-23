package ru.alfabank.myjob.alfabanktesttask.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.alfabank.myjob.alfabanktesttask.model.ExchangeRate;
import ru.alfabank.myjob.alfabanktesttask.service.ExchangeRateServices;

import java.time.LocalDateTime;

import static ru.alfabank.myjob.alfabanktesttask.utils.Util.getExchangeRateURI;

@SpringBootTest
public class ExchangeRateServicesTest {
    @Value("${exchange.server}") private String exServer;
    @Value("${exchange.app_id}") private String exchangeAppID;
    @Value("${exchange.base}") private String rateBase;

    @Autowired
    public ExchangeRateServices exchangeRateServices;

    @Test
    void getExchange() {
        ExchangeRate exchangeRate = exchangeRateServices.getExchange(getExchangeRateURI(exServer, exchangeAppID, rateBase, LocalDateTime.now()));
        Assertions.assertNotNull(exchangeRate);
    }
}
