package com.cts.ora.report.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.ora.report.Application;
import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.filter.FilterResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class ORAFilterControllerTest {
	
	Logger logger = LoggerFactory.getLogger(ORAFilterControllerTest.class);
	
	@Autowired
    protected ApplicationContext ac;
	
	@Autowired
	ORAFilterController controller;
	
	//@Autowired
    //private TestEntityManager entityManager;

	@Test
	public void testGetAssociates() {
		logger.info("Into testGetAssociates");
		FilterResponse response = controller.getORAUsers();
		assertNotNull(response);
		
		assertEquals(response.getStatus(), "SUCCESS");
		
		logger.info("Out of testGetAssociates");
	}
	
	@Test
	public void testGetBusinessUnits() {
		logger.info("Into testGetBusinessUnits");
		ORAResponse response = controller.getBusinessUnits();
		assertNotNull(response);
		
		assertEquals(response.getStatus(), "SUCCESS");
		
		logger.info("Out of testGetBusinessUnits");
	}

}
