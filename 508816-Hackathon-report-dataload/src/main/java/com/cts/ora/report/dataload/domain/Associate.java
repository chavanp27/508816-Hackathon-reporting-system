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
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name="ora_outreach_associate")
@Data
@EqualsAndHashCode(of= {"id","name","designation"})
//@Cacheable
public class Associate {
	
	@Id
	@Column(name="asc_id")
	private Integer id;
	
	@Column @NotBlank
	private String name;
	
	@Column
	private String designation;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	@Transient
	private boolean isBuExists;

}
