package com.cts.ora.report.dataload.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ORA_UI_USER")
@Getter @Setter @ToString @EqualsAndHashCode(of={"userName","userId"})
public class ORAUser {
	
	@Id 
	private String userName;
	
	@OneToOne
	@JoinColumn(name="emp_id")
	private Associate userId;
	
	private String firstName;
	
	private String lastName;
	
	@OneToOne
	@JoinColumn(name="roleId")
	private ORARoleDef role;
	
	private String status;
	
	@Column @CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;

}
