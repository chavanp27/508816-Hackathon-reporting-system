package com.cts.ora.report.domain.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ora_outreach_associate")
@Getter @Setter @ToString(exclude= {"bu","events"})
@EqualsAndHashCode(of= {"id"})
@SqlResultSetMapping(name = "AssociateMapping", entities = {
		@EntityResult(entityClass = com.cts.ora.report.domain.model.Associate.class, fields = {
				@FieldResult(name = "id", column = "asc_id"), 
				@FieldResult(name = "ascName", column = "ASC_NAME"),
				@FieldResult(name = "isVolunteer", column = "is_volunteer"),
				@FieldResult(name = "isPOC", column = "is_poc"),
				@FieldResult(name = "createdDate", column = "created_date"),
				@FieldResult(name = "designation", column = "designation"),
				@FieldResult(name = "firstVolunteerDate", column = "first_volunteer_date"),
				@FieldResult(name = "bu", column = "asc_bId") }) })
public class Associate {
	
	@Id
	@Column(name="asc_id")
	private Long id;
	
	@Column @NotBlank
	private String ascName;
	
	@Column
	private String designation;
	
	@OneToOne @JsonIgnore 
	@JoinColumn(name="buId",insertable=true,updatable=true)
	private BusinessUnit bu;
	
	@Column(name="is_volunteer")
	@Type(type= "org.hibernate.type.NumericBooleanType")
	private Boolean isVolunteer;
	
	@Column(name="is_poc")
	@Type(type= "org.hibernate.type.NumericBooleanType")
	private Boolean isPOC;
	
	@Column @CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date createdDate;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	private Date firstVolunteerDate;
	
	@Transient @JsonIgnore
	private boolean isBuExists;
	
	@OneToMany @JsonIgnore
	@JoinColumn(name="eventId")
	private Set<EventInfo> events;

}
