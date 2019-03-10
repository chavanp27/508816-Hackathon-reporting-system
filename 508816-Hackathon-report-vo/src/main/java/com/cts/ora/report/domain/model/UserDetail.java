package com.cts.ora.report.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor
public class UserDetail {
	
	private Long id;
	
	private String name;
	
	private boolean isPOC;
	
	private boolean isPMO;
	
	private boolean isAdmin;
	
	private String roleName;

}
