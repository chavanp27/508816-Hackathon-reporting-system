package com.cts.ora.report.domain.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(
		name="ORA_OUTREACH_REF_PROJECTS", 
        uniqueConstraints=
            @UniqueConstraint(columnNames={"project_title"})
    )
@Data @EqualsAndHashCode(of={"title"}) @ToString(exclude= {"categories"})
public class Project {
	
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer proj_id;
	
	@Column(name="project_title") @NotBlank
	private String title;
	
	private String description;
	
	private String focusArea;
	
	@Column
	private String status;
	
	private Long createdBy;
	
	@OneToMany(mappedBy="project") @JsonIgnore
	private Set<EventCategory> categories;
	
	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	@Transient
	private boolean isPersist;
	@Transient
	private boolean isUpdate;

}
