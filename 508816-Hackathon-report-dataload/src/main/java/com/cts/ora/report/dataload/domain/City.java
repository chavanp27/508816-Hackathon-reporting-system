package com.cts.ora.report.dataload.domain;

import java.util.Date;
import java.util.Set;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity()
@Table(name="ORA_REF_GEO_CITY", 
uniqueConstraints=
     @UniqueConstraint(columnNames={"name"})
)
@Data @EqualsAndHashCode(of={"name"})
public class City {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer cityId;
	
	@Column @NotBlank
	private String name;
	
	@Column @CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	private Long createdBy;
	
	@OneToOne @JsonIgnore 
	@JoinColumn(name="stateId",insertable=true,updatable=true)
	private State state;
	
	@OneToMany(mappedBy="city") @JsonIgnore
	private Set<ResidenceArea> resAreas;

}
