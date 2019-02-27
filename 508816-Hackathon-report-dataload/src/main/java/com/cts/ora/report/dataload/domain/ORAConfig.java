package com.cts.ora.report.dataload.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity(name="ora_sys_config")
@Data
public class ORAConfig {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String configParam;
	
	private String configValue;
	
	private String description;

}
