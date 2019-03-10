package com.cts.ora.report.domain.model;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Getter @Setter @ToString @AllArgsConstructor
public class UserDetail {
	
	private Long id;
	
	private String name;
	
	private boolean isPOC;
	
	private boolean isPMO;
	
	private boolean isAdmin;
	
	private String roleName;

}
