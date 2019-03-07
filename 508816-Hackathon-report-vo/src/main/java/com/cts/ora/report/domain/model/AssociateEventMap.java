package com.cts.ora.report.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity()
@Table(
		name="ORA_OUTREACH_ASSOCIATE_EVENT_MAP", 
        uniqueConstraints=
            @UniqueConstraint(columnNames={"asc_id","eventId"})
    )
@Data @EqualsAndHashCode(of={"asc","event"}) @ToString(exclude={"asc","event"})
public class AssociateEventMap {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long mapId;
	
	@OneToOne 
	@JoinColumn(name="asc_id",insertable=false,updatable=false)
	private Associate asc;
	
	@OneToOne
	@JoinColumn(name="eventId",insertable=false,updatable=false)
	private EventInfo event;
	
	private Float volHours;
	
	private Float travelHours;
	
	private Integer livesImpactedCount;	
	

	@Column @CreationTimestamp
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date createdDate;
}
