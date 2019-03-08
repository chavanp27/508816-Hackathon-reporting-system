package com.cts.ora.report.fetch.vo;



import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.Location;

import lombok.Data;

@Data
public class ParticipationMetrics {
	
	private Integer period;
	
	private Location location;
	
	private BusinessUnit bussinessUnit;
	
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
