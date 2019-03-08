package com.cts.ora.report.fetch.vo;



import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.Location;

import lombok.Data;

@Data
public class RetentionMetrics {

	private Integer period;
	private Location location;
	private BusinessUnit bussinessUnit;
	private Integer noVolunOnceQuarter;
	private Integer percentVolunOnceQuarter;
	private Integer noVolunOnceMonth;
	private Integer percentVolunOnceMonth;
	private Integer avgAsscVolunteer;
	private Integer sinceLastVolunteer;
}
