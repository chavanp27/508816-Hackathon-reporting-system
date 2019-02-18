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

@Entity(name="ORA_OUTREACH_REF_EVENT_CAT")
@Data
public class EventCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String title;
	
	private String description;
	
	private String focusArea;
	
	private Character status;
	
	private String createdBy;
	
	@Column 
	@Temporal(TemporalType.DATE)
	private Date createdDate;

}
