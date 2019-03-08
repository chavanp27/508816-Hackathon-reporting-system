package com.cts.ora.report.fetch.vo;



import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.Location;

import lombok.Data;

@Data
public class EngagementMetrics {
	
	private Integer period;
	private Location location;
	private BusinessUnit bussinessUnit;
	private Integer oneTimersCount;
	private Integer twoToFiveTimersCount;
	private Integer fivePlusTimersCount;
}
