package com.cts.ora.report.vo;

import com.cts.ora.report.common.vo.ORAResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserConfig extends ORAResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long ascId;
	private String userName;
	private String displayName;
	private boolean isAdmin;
	private String roleName;

}
