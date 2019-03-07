package com.cts.ora.report.dataload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@ComponentScan(basePackages="com.cts.ora.report")
@EntityScan("com.cts.ora.report.domain")
@EnableJpaRepositories
@EnableTransactionManagement
public class ReportDataloadApplication {
	
	Logger log = LoggerFactory.getLogger(ReportDataloadApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReportDataloadApplication.class, args);
	}

}

