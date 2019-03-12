package com.cts.ora.report.fetch.vo;



import lombok.Data;

@Data
public class ParticipationMetrics {
	
	private String period;
	
	private Integer location;
	
	private String bussinessUnit;
	
	private String project;
	
	private String category;
	
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
