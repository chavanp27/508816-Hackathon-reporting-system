package com.cts.ora.report.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import lombok.Data;
@Entity(name="ora_bu_metrics")
@Data
public class BuMetrics {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="period")
	private Integer period;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bu_id")
	private BusinessUnit buId;
	
	@Column(name="head_count")
	private Integer headCount;
	
	@Column(name="uni_volunteers")
	private Integer uniqueVolunteers;
	
	@Column(name="vol_hours")
	private Integer volunteerHours;
	
	@Column(name="trav_hours")
	private Integer travelHours;
	
	@Column(name="total_events")
	private Integer totalEvents;
	
	@Column(name="is_weekend")
	@Type(type= "org.hibernate.type.NumericBooleanType")
	private Boolean isWeekend;
}
