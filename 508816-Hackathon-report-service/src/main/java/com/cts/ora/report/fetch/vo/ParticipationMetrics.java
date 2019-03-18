package com.cts.ora.report.fetch.vo;



import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.EventCategory;
import com.cts.ora.report.domain.model.Location;
import com.cts.ora.report.domain.model.Project;

import lombok.Data;

@Data
public class ParticipationMetrics {
	
	private String period;
	
	private Location location;
	
	private BusinessUnit bussinessUnit;
	
	private Project project;
	
	private EventCategory category;
	
	private Integer headCount;
	
	private Integer uniqueVolunteers;
	
	private Integer volunteerHours;
	
	private Integer totalHours;

	private Integer coverage;
	
	private Integer avgFreqPerVolunteer;
	
	private Integer avgHoursPerAssc;
	
	private Integer avgHoursPerVolunteer;
	
	private Integer travelHours;
	
	private Integer totalEvents;
	
	private Integer avgHoursPerEvnt;
	
	private Integer avgVolunPerEvnt;
	
	private Integer avgVolunHrsPerVolunPerEvnt;

	
	
}
