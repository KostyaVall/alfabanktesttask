package ru.alfabank.myjob.alfabanktesttask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.alfabank.myjob.alfabanktesttask.controller.ExchangeRateController;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AlfaBankTestTaskApplicationTests {

	@Autowired
	private ExchangeRateController exchangeRateController;

	@Test
	void contextLoads() {
		assertNotNull(exchangeRateController);
	}

}
