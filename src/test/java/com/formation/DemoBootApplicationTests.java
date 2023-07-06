package com.formation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.formation.service.Calculator;

@SpringBootTest
class DemoBootApplicationTests {
	
	Calculator calculator = new Calculator();

	@Test
	void testSum() {
		
		assertEquals(6, calculator.sum(2, 4));
	}

}
