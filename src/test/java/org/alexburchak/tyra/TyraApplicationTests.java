package org.alexburchak.tyra;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

@SpringBootTest(classes = TyraApplication.class)
@WebAppConfiguration
public class TyraApplicationTests extends AbstractTestNGSpringContextTests {
	@Test
	public void contextLoads() {
	}
}
