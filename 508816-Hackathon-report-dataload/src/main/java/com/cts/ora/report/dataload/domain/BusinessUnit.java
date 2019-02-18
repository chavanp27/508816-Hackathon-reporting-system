package com.cts.ora.report.dataload.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name="ora_ref_assc_bu")
@Data
@EqualsAndHashCode(exclude= {"associates"})
public class BusinessUnit {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer buId;
	
	@Column @NotBlank
	private String name;
	
	@Column
	private String description;
	
	//@OneToMany(fetch=FetchType.LAZY,mappedBy="bu")
	//private Set<Associate> associates; 

}
