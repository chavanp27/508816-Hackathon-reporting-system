package com.cts.ora.report.dataload.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(
		name="ORA_SYS_REPORT_TYPE", 
        uniqueConstraints=
            @UniqueConstraint(columnNames={"reportType"})
    )
@Data @EqualsAndHashCode(of={"reportType"})
public class ReportType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(
	    name = "native", strategy = "native"
	)
	private Integer repId;
	
	@Column @NotBlank
	private String reportType;
	
	@Column
	private String description;

}
