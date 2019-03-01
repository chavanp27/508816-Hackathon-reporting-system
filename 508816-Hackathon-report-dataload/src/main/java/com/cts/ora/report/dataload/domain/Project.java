package com.cts.ora.report.dataload.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity(name="ORA_OUTREACH_REF_PROJECTS")
@Data
public class Project {
	
	@Id @Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="project_title") @NotBlank
	private String title;
	
	@Column
	private String description;
	
	@Column(name="focus_area")
	private String focusArea;
	
	@Column
	private Character status;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_date") 
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	//@OneToMany(mappedBy="project")
	//private Set<EventInfo> events;

}
