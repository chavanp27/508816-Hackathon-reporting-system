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

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity(name="ORA_OUTREACH_REF_PROJECTS")
@Data
public class Project {
	
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer id;
	
	@Column(name="project_title") @NotBlank
	private String title;
	
	private String description;
	
	private String focusArea;
	
	@Column
	private Character status;
	
	private String createdBy;
	
	@Column(name="created_date") 
	@Temporal(TemporalType.DATE)
	private Date createdDate;


}
