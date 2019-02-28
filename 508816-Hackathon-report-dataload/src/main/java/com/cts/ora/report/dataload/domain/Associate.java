package com.cts.ora.report.dataload.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ora_outreach_associate") @Table(name="ora_outreach_associate")
@Getter @Setter @ToString
@EqualsAndHashCode(of= {"id","name","designation"})
@SqlResultSetMapping(
        name = "AssociateMapping",
        entities ={ @EntityResult(
                entityClass = com.cts.ora.report.dataload.domain.Associate.class,
                fields = {
	                    @FieldResult(name = "id", column = "asc_id"),
	                    @FieldResult(name = "name", column = "name"),
	                    @FieldResult(name = "designation", column = "designation"),
	                    @FieldResult(name = "isVolunteer", column = "is_volunteer"),
	                    @FieldResult(name = "isPOC", column = "is_poc"),
	                    @FieldResult(name = "createdDate", column = "created_date")
	                    //,@FieldResult(name = "bu.buId", column = "bu_id")
                    }),
                    @EntityResult(
                entityClass = com.cts.ora.report.dataload.domain.BusinessUnit.class,
                fields = {
	                    @FieldResult(name = "buId", column = "bu_id")
                    })
                    
                    })

public class Associate {
	
	@Id
	@Column(name="asc_id")
	private Integer id;
	
	@Column @NotBlank
	private String name;
	
	@Column
	private String designation;
	
	@ManyToOne
	@JoinColumn(name="buId",insertable=true,updatable=true)
	private BusinessUnit bu;
	
	@Column(name="is_volunteer")
	@Type(type= "org.hibernate.type.NumericBooleanType")
	private Boolean isVolunteer;
	
	@Column(name="is_poc")
	@Type(type= "org.hibernate.type.NumericBooleanType")
	private Boolean isPOC;
	
	@Column @CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	@Transient
	private boolean isBuExists;

}
