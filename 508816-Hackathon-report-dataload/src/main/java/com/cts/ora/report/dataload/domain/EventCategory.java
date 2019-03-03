package com.cts.ora.report.dataload.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="ORA_OUTREACH_REF_EVENT_CAT", 
       uniqueConstraints=
            @UniqueConstraint(columnNames={"title"})
    )
@Data @EqualsAndHashCode(of={"title"}) @ToString(exclude={"project"})
public class EventCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer cat_id;
	
	@NotBlank
	private String title;
	
	@ManyToOne @JsonIgnore
	@JoinColumn(name="proj_id",insertable=true,updatable=true)
	private Project project;
	
	private String description;
	
	private String status;
	
	private Long createdBy;
	
	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	@Transient
	private boolean isPersist;
	@Transient
	private boolean isUpdate;

}
