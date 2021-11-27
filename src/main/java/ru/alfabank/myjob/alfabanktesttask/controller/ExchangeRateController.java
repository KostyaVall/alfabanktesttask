package ru.alfabank.myjob.alfabanktesttask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alfabank.myjob.alfabanktesttask.model.Gif;
import ru.alfabank.myjob.alfabanktesttask.service.ExchangeRateServices;
import ru.alfabank.myjob.alfabanktesttask.service.GifService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.alfabank.myjob.alfabanktesttask.utils.Util.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateController {

    @Value("${exchange.server}") private String exServer;
    @Value("${exchange.app_id}") private String exchangeAppID;
    @Value("${exchange.base}") private String rateBase;
    @Value("${gif.server}") private String gifServer;
    @Value("${gif.app_id}") private String gifApiID;
    @Value("${gif.rich}") private String rich;
    @Value("${gif.broken}") private String broken;

    @Autowired
    private final ExchangeRateServices exchangeRateService;
    @Autowired
    private final GifService gifService;


    public ExchangeRateController(ExchangeRateServices exchangeRateService, GifService gifService) {

        this.exchangeRateService = exchangeRateService;
        this.gifService = gifService;
    }

    @GetMapping
    public Gif getGif(){
        return getGifAsJSON("RUB");
    }

    @GetMapping("/{currency}")
    public Gif getGifAsJSON(@PathVariable String currency) {

        String tag = getGifTag(
                compareExchangeRate(
                        exchangeRateService.getExchange(
                                getExchangeRateURI(exServer, exchangeAppID, rateBase, LocalDateTime.now())
                        ),
                        exchangeRateService.getExchange(
                                getExchangeRateURI(exServer, exchangeAppID, rateBase, LocalDateTime.now().minusDays(1))
                        ),
                        currency
                )
        );

        Gif gif = gifService.getGif(
                getGifURI(gifServer, gifApiID, tag)
        );

        gif.setTag(tag);

        return gif;
    }

    @GetMapping("/getcodes")
    public List<String> getCodes() {

        return getCodesUtils(
                exchangeRateService.getExchange(
                        getExchangeRateURI(exServer, exchangeAppID, rateBase, LocalDateTime.now())
                )
        );
    }

    public String getGifTag(boolean b){
        return b ? rich : broken;
    }
}
