package com.cts.ora.dataload.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.dataload.ReportDataloadApplication;
import com.cts.ora.report.dataload.controller.ORADataLoadController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ReportDataloadApplication.class)
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class ORADataLoadControllerTest {
	
	@TestConfiguration
    static class ORATestContextConfiguration {
  
        @Bean
        public ORADataLoadController oraDataLoadController() {
            return new ORADataLoadController();
        }
    }
	
	Logger logger = LoggerFactory.getLogger(ORADataLoadControllerTest.class);
	
	@Autowired
    protected ApplicationContext ac;
	
	@Autowired
	ORADataLoadController oraDataLoadController;
	
	//@Autowired
    //private TestEntityManager entityManager;

	@Test
	public void testGetAssociates() {
		logger.info("Into testGetAssociates");
		ORAResponse response = oraDataLoadController.getAssociates();
		assertNotNull(response);
		
		assertEquals(response.getStatus(), "SUCCESS");
		
		logger.info("Out of testGetAssociates");
	}

}
