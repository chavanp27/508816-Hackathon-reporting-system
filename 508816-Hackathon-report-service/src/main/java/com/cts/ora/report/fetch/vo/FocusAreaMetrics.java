package com.cts.ora.report.fetch.vo;

import com.cts.ora.report.domain.model.EventCategory;
import com.cts.ora.report.domain.model.Project;

import lombok.Data;
@Data
public class FocusAreaMetrics {

	
	private Integer period;
	
	private Project project;
	
	private EventCategory category;
	
	private Integer headCount;
	
	private Integer uniqueVolunteers;
	
	private Integer volunteerHours;
	
	private Integer travelHours;
	
	private Integer totalEvents;
	
	private Boolean isWeekend;
}
