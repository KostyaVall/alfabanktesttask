package ru.alfabank.myjob.alfabanktesttask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.alfabank.myjob.alfabanktesttask.model.DataClient;
import ru.alfabank.myjob.alfabanktesttask.model.ExchangeRate;
import ru.alfabank.myjob.alfabanktesttask.model.Gif;
import ru.alfabank.myjob.alfabanktesttask.service.ExchangeRateServices;
import ru.alfabank.myjob.alfabanktesttask.service.GifService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.alfabank.myjob.alfabanktesttask.utils.Util.getExchangeRateURI;
import static ru.alfabank.myjob.alfabanktesttask.utils.Util.getGifURI;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ExchangeRateControllerTest {

    @Value("${exchange.server}") private String exServer;
    @Value("${exchange.app_id}") private String exchangeAppID;
    @Value("${exchange.base}") private String rateBase;
    @Value("${gif.server}") private String gifServer;
    @Value("${gif.app_id}") private String gifApiID;
    @Value("${gif.rich}") private String rich;
    @Value("${gif.broken}") private String broken;

    Map<String, Double> exMap = new HashMap<>();
    Map<String, Double> exMap1 = new HashMap<>();

    {
        exMap.put("RUB", 73.36);
        exMap1.put("RUB", 75.21);
    }
    ExchangeRate exchangeRate = new ExchangeRate("disclaimer", "license", "USD", exMap);
    ExchangeRate exchangeRate1 = new ExchangeRate("disclaimer", "license", "USD", exMap1);

    DataClient testData = new DataClient(
            "QAO5e1GbF9kLKDjqlI",
            "Tiwa Savage Money GIF by Universal Music Africa",
            "g",
            "https://giphy.com/embed/QAO5e1GbF9kLKDjqlI",
            "https://giphy.com/gifs/universalafrica-umgsa-universalmusicsouthafrica-umgnigeria-QAO5e1GbF9kLKDjqlI"
    );
    Gif testGif = new Gif(testData, rich);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ExchangeRateServices exchangeRateServices;
    @MockBean
    private GifService gifService;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @BeforeEach
    void beforeEach() {
        URI exToday = getExchangeRateURI(exServer, exchangeAppID, rateBase, LocalDateTime.now());
        URI exYest = getExchangeRateURI(exServer,exchangeAppID,rateBase, LocalDateTime.now().minusDays(1));
        URI gifURI = getGifURI(gifServer, gifApiID, broken);

        when(exchangeRateServices.getExchange(exToday)).thenReturn(exchangeRate);
        when(exchangeRateServices.getExchange(exYest)).thenReturn(exchangeRate1);
        when(gifService.getGif(gifURI)).thenReturn(testGif);
    }

    @Test
    void getGif() throws Exception {
        perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(testGif)));
    }

    @Test
    void getGifAsJSON() throws Exception {
        perform(MockMvcRequestBuilders.get("/rub"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(testGif)));
    }

}
