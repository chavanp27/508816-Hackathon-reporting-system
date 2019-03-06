package com.cts.ora.report.dataload.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity()
@Table(
		name="ORA_SYS_TEMPLATE_DOCS", 
        uniqueConstraints=
            @UniqueConstraint(columnNames={"docName"})
    )
@Data @EqualsAndHashCode(of={"docName"}) 
public class ORADocTemplate {
	
	@Id 
	private Integer id;
	
	private String docName;
	
	private String docType;
	
	private String location;
	
	@Column @CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;

}
