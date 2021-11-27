package ru.alfabank.myjob.alfabanktesttask.service;

import org.springframework.http.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alfabank.myjob.alfabanktesttask.model.ExchangeRate;

import java.net.URI;

@FeignClient(name = "ExchangeRate", url = "https://openexchangerates.org/api/")
public interface ExchangeRateServices {

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ExchangeRate getExchange(URI baseUrl);
}
