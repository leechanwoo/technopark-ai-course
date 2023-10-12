package com.example.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class TestApplicationTests {

	// @Test
	void thisMustBeFailed() {
		int i = 1;
		assertEquals(0, i, String.format("the value of i is %d", i));
	}

}
