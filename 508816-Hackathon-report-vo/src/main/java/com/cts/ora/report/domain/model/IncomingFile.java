package com.cts.ora.report.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ORA_SYS_INCOMING_FILES")
@Getter @Setter @ToString @EqualsAndHashCode(of={"inboundId"})
public class IncomingFile {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long inboundId;
	
	private String fileName;
	
	private String fileLoc;
	
	private String status; //Valid or Invalid
	
	@OneToOne
	@JoinColumn(name="repId",insertable=true,updatable=true)
	private ReportType repType;
	
	private Long uploadBy;
	
	@Column @CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:SS")
	private Date createdDate;

}
