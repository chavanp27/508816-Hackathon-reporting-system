package com.cts.ora.report.dataload.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name="ORA_REF_GEO_RES_AREA", 
uniqueConstraints=
     @UniqueConstraint(columnNames={"name"})
)
@Data @EqualsAndHashCode(of={"name"}) @ToString(exclude={"pinCode"})
public class ResidenceArea {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer areaId;
	
	@Column @NotBlank
	private String name;
	
	@Column @CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	@Transient
	private List<String> zipCode;
	
	private Long createdBy;
	
	@OneToOne @JsonIgnore 
	@JoinColumn(name="cityId",insertable=true,updatable=true)
	private City city;
	
	@OneToOne(mappedBy="area") @JsonIgnore
	private PinCode pinCode;
	
	@Transient
	private boolean isPersist;
	@Transient
	private boolean isUpdate;

}
