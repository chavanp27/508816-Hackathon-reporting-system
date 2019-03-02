package com.cts.ora.report.dataload.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ora_outreach_event_info")
@Getter @Setter @ToString
public class EventInfo {
	
	@Id
	private String eventId;
	
	@OneToOne
	@JoinColumn(name="id",insertable=true,updatable=true)
	private Project project;
	
	@NotBlank
	private String title;
	
	private String description;
	
	private Integer period;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date eventDate;
	
	private String beneficiaryName;
	
	private Integer volunteersRequired;
	
	private String baseLoc;
	
	@OneToOne
	@JoinColumn(name="locId",insertable=true,updatable=true)
	private Location location;
	
	@OneToMany(mappedBy="id") @JsonIgnore
	private Set<Associate> poc;
	
	@OneToMany(mappedBy="id") @JsonIgnore
	private Set<Associate> volunteers;
	
	private Integer livesImpacted;
	
	@Column(name="on_weekend")
	@Type(type= "org.hibernate.type.NumericBooleanType")
	private Boolean onWeekend;
	
	private String eventAddress;
	
	@Column @CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	private Long createdBy;

}
