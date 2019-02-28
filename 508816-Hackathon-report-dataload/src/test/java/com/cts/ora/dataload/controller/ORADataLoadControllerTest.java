package com.cts.ora.dataload.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import com.cts.ora.report.dataload.controller.ORADataLoadController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ORADataLoadController.class)
public class ORADataLoadControllerTest {
	
	Logger logger = LoggerFactory.getLogger(ORADataLoadControllerTest.class);
	
	@Autowired
    protected WebApplicationContext wac;
	
	@Autowired
	ORADataLoadController controller;
	
	@Autowired
    private TestEntityManager entityManager;

	@Test
	public void testHelloWorld() {
		
		Object response = controller.getAssociates();
		assertEquals(response, "Hello World");
	}

}
