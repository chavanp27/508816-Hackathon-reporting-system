package com.cts.ora.report.dataload.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity()
@Table(name="ORA_REF_GEO_STATE", 
uniqueConstraints=
     @UniqueConstraint(columnNames={"name"})
)
@Data @EqualsAndHashCode(of={"name"}) @ToString(exclude={"cities"})
public class State {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer stateId;
	
	@Column @NotBlank
	private String name;
	
	@Column @CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	private Long createdBy;
	
	@OneToOne @JsonIgnore 
	@JoinColumn(name="cntryId",insertable=true,updatable=true)
	private Country country;
	
	@OneToMany(mappedBy="state") @JsonIgnore
	private Set<City> cities;
	
	@Transient
	private boolean isPersist;
	@Transient
	private boolean isUpdate;

}
