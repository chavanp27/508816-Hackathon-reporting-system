package com.cts.ora.report.dataload.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity()
@Table(
		name="ora_ref_assc_bu", 
        uniqueConstraints=
            @UniqueConstraint(columnNames={"name"})
    )
@Data @ToString
@EqualsAndHashCode(of= {"name"})
public class BusinessUnit {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(
	    name = "native", strategy = "native"
	)
	private Integer buId;
	
	@Column @NotBlank
	private String name;
	
	@Column
	private String description;
	
	@OneToMany(mappedBy="bu")
	private Set<Associate> associates; 

}
