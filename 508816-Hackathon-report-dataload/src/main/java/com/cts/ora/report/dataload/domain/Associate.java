package com.cts.ora.report.dataload.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name="ORA_OUTREACH_ASSOCIATE")
//@Table(appliesTo="ora_outreach_associate")
@Data
@EqualsAndHashCode(of= {"id","name","designation"})
//@Cacheable
public class Associate {
	
	@Id
	@NotBlank
	@Column(name="asc_id")
	private Integer id;
	
	@Column @NotBlank
	private String name;
	
	@Column
	private String designation;
	
	@Column
	private Integer bu_id;
	
	//@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name="bu_id",insertable=false,updatable=false)
	//private BusinessUnit bu;
	
	@Column(name="is_volunteer")
	@Type(type= "org.hibernate.type.NumericBooleanType")
	private Boolean isVolunteer;
	
	@Column(name="is_poc")
	@Type(type= "org.hibernate.type.NumericBooleanType")
	private Boolean isPOC;
	
	@Column 
	@Temporal(TemporalType.DATE)
	private Date createdDate;

}
