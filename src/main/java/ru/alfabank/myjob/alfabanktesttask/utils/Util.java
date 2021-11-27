package ru.alfabank.myjob.alfabanktesttask.utils;

import org.springframework.util.Assert;
import ru.alfabank.myjob.alfabanktesttask.model.ExchangeRate;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Util {

    public static URI getExchangeRateURI(String exServer, String exchangeAppID, String rateBase, LocalDateTime date) {

        Assert.notNull(exServer,"field Server is null");
        Assert.notNull(exchangeAppID,"field exchangeAppID is null");
        Assert.notNull(rateBase,"field rateBase is null");
        Assert.notNull(date,"field date is null");
        StringBuilder builderURL = new StringBuilder();

        if (date.toLocalDate().isEqual(LocalDate.now())) {
            builderURL
                    .append(exServer)
                    .append("latest.json?app_id=")
                    .append(exchangeAppID)
                    .append("&base=")
                    .append(rateBase.toUpperCase());
            return URI.create(builderURL.toString());
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        builderURL
                .append(exServer)
                .append("historical/")
                .append(timeFormatter.format(date))
                .append(".json?app_id=")
                .append(exchangeAppID)
                .append("&base=")
                .append(rateBase.toUpperCase());

        return URI.create(builderURL.toString());
    }

    public static URI getGifURI(String gifServer, String gifApiID, String tag) {

        Assert.notNull(gifServer,"field gifServer is null");
        Assert.notNull(gifApiID,"field gifApiID is null");
        Assert.notNull(tag,"field tag is null");
        StringBuilder builderURL = new StringBuilder();
        builderURL
                .append(gifServer)
                .append("v1/gifs/random?api_key=")
                .append(gifApiID)
                .append("&tag=")
                .append(tag);

        return URI.create(builderURL.toString());
    }

    public static boolean compareExchangeRate(ExchangeRate rate1, ExchangeRate rate2, String currency) {

        //Проверяем на >=, так как на выходных и праздниках курс не меняется. Будем считать, что это хорошо)
        return rate1.getRates().get(currency.toUpperCase()) >= rate2.getRates().get(currency.toUpperCase());
    }

    public static List<String> getCodesUtils(ExchangeRate exchangeRate) {

        List<String> result = null;

        if (exchangeRate.getRates() != null) {
            result = new ArrayList<>(exchangeRate.getRates().keySet());
        }

        return result;
    }
}
