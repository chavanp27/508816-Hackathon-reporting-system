package com.cts.ora.report.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity(name="ORA_SYS_DATALOAD_LOG")
@Getter @Setter 
public class DataLoadLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(
	    name = "native", strategy = "native"
	)
	@Column(name="log_id")
	private Integer id;
	
	private String fileName;
	
	private String status;
	
	@OneToOne
	@JoinColumn(name="repId",insertable=false,updatable=false)
	private IncomingFile iF;
}
