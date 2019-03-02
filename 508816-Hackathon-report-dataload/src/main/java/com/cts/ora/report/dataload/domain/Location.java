package com.cts.ora.report.dataload.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ora_ref_location")
@Getter @Setter @ToString
public class Location {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer locId;
	
	@ManyToOne
	@JoinColumn(name="cntryId",insertable=true,updatable=true)
	private Country countryId;
	
	@ManyToOne
	@JoinColumn(name="stateId",insertable=true,updatable=true)
	private State sId;
	
	@ManyToOne
	@JoinColumn(name="cityId",insertable=true,updatable=true)
	private City cId;
	
	@ManyToOne
	@JoinColumn(name="resAreaId",insertable=true,updatable=true)
	private ResidenceArea resAreaId;
	
	@OneToMany(mappedBy="eventId") @JsonIgnore
	private Set<EventInfo> events;

}
