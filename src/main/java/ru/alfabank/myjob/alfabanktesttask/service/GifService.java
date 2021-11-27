package ru.alfabank.myjob.alfabanktesttask.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alfabank.myjob.alfabanktesttask.model.Gif;

import java.net.URI;

@FeignClient(name = "Gif", url = "https://api.giphy.com/")
public interface GifService {

    @GetMapping(consumes= MediaType.APPLICATION_JSON_VALUE)
    Gif getGif(URI baseUrl);
}
