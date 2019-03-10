package com.cts.ora.report.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ORA_UI_USER")
@SqlResultSetMapping(name = "UserDetailsMapping", classes = {
		@ConstructorResult(targetClass = com.cts.ora.report.domain.model.UserDetail.class, 
				columns = {
						@ColumnResult(name = "asc_id", type = Long.class), 
						@ColumnResult(name = "asc_name", type = String.class),
						@ColumnResult(name = "is_poc", type = Boolean.class),
						@ColumnResult(name = "is_pmo", type = Boolean.class),
						@ColumnResult(name = "is_admin", type = Boolean.class),
						@ColumnResult(name = "role_name", type = String.class)
						})
		})
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
