package com.cts.ora.report.dataload.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ora_ref_location")
@Getter @Setter @ToString
public class Location {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="loc_id")
	private Integer locId;
	
	@Column
	private Integer countryId;
	
	@Column
	private Integer stateId;
	
	@Column
	private Integer cityId;
	
	@Column
	private Integer resAreaId;

}
