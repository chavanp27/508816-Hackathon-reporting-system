package com.cts.ora.report.dataload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@ComponentScan(basePackages="com.cts.ora.report.dataload")
@EnableTransactionManagement
public class ReportDataloadApplication {
	
	Logger log = LoggerFactory.getLogger(ReportDataloadApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReportDataloadApplication.class, args);
	}

}

