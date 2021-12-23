package ru.alfabank.myjob.alfabanktesttask.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.alfabank.myjob.alfabanktesttask.model.Gif;
import ru.alfabank.myjob.alfabanktesttask.service.GifService;

import static ru.alfabank.myjob.alfabanktesttask.utils.Util.getGifURI;

@SpringBootTest
public class GifServiceTest {
    @Value("${gif.server}") private String gifServer;
    @Value("${gif.app_id}") private String gifApiID;
    @Value("${gif.rich}") private String rich;
    @Value("${gif.broken}") private String broken;

    @Autowired
    GifService gifService;

    @Test
    void getGifRich() {
        Gif gif = gifService.getGif(getGifURI(gifServer, gifApiID, rich));
        Assertions.assertNotNull(gif);
    }

    @Test
    void getGifBroken() {
        Gif gif = gifService.getGif(getGifURI(gifServer, gifApiID, broken));
        Assertions.assertNotNull(gif);
    }
}
