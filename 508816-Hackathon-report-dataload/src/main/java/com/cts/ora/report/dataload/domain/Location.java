package com.cts.ora.report.dataload.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity()
@Table(name="ora_ref_location", 
uniqueConstraints=
     @UniqueConstraint(columnNames={"cntryId","stateId","cityId","resAreaId","codeId"})
)
@Data @EqualsAndHashCode(exclude={"locId","isPersist","isUpdate"})
public class Location {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="native")
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
	
	@OneToOne
	@JoinColumn(name="resAreaId",insertable=true,updatable=true)
	private ResidenceArea resAreaId;
	
	@OneToOne
	@JoinColumn(name="codeId",insertable=true,updatable=true)
	private PinCode codeId;
	
	@OneToMany(mappedBy="eventId") @JsonIgnore
	private Set<EventInfo> events;
	
	@Transient
	private boolean isPersist;
	
	@Transient
	private boolean isUpdate;

}
