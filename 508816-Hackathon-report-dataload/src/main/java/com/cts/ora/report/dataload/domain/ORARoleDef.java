package com.cts.ora.report.dataload.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ORA_UI_ROLE_DEF")
@Getter @Setter @ToString @EqualsAndHashCode(of={"roleName"})
public class ORARoleDef {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer roleId;
	
	private String roleName;
	
	private String description;

}
