package com.cts.ora.report.domain.model;

import java.util.Date;

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

@Entity()
@Table(name="ORA_REF_GEO_PINCODE", 
       uniqueConstraints=
            @UniqueConstraint(columnNames={"name"})
    )
@Data @EqualsAndHashCode(of={"name"})
public class PinCode {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer codeId;
	
	@Column @NotBlank
	private String name;
	
	@Column @CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	private Long createdBy;
	
	@OneToOne @JsonIgnore 
	@JoinColumn(name="areaId",insertable=true,updatable=true)
	private ResidenceArea area;
	
	@Transient
	private boolean isPersist;
	@Transient
	private boolean isUpdate;
}
