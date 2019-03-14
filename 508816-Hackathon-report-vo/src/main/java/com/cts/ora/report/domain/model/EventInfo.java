package com.cts.ora.report.domain.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity()
@Table(
		name="ora_outreach_event_info", 
        uniqueConstraints=
            @UniqueConstraint(columnNames={"eventId"})
    )
@Getter @Setter @ToString(exclude= {"poc","volunteers"}) @EqualsAndHashCode(of={"eventId"})
public class EventInfo {
	
	@Id
	private String eventId;
	
	@OneToOne
	@JoinColumn(name="proj_id",insertable=true,updatable=true)
	private Project project;
	
	@OneToOne
	@JoinColumn(name="cat_id",insertable=true,updatable=true)
	private EventCategory category;
	
	@NotBlank
	private String eventName;
	
	private String description;
	
	private Integer period;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yy")
	private Date eventDate;
	
	private String beneficiaryName;
	
	private Integer volunteersRequired;
	
	private String baseLoc;
	
	@OneToOne
	@JoinColumn(name="locId",insertable=true,updatable=true)
	private Location location;
	
	@OneToMany(mappedBy="id",fetch=FetchType.EAGER) @JsonIgnore
	private Set<Associate> poc;
	
	@OneToMany(mappedBy="id",fetch=FetchType.EAGER) 
	private Set<Associate> volunteers;
	
	private Integer totalVolunteersCount;
	
	private Float totalVolunteerHrs;
	
	private Float totalTravelHrs;
	
	private Float totalEventHrs;
	
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
