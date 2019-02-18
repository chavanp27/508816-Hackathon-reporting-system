package com.cts.ora.report.dataload.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity(name="ORA_OUTREACH_REF_PROJECTS")
@Data
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="project_tile") @NotBlank
	private String title;
	
	private String description;
	
	private String focusArea;
	
	private String status;
	
	private String createdBy;
	
	@Column 
	@Temporal(TemporalType.DATE)
	private Date createdDate;

}
