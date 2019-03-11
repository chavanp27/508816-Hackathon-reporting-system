package com.cts.ora.report.fetch.vo;



import lombok.Data;

@Data
public class RetentionMetrics {

	private Integer period;
	private Integer location;
	private String bussinessUnit;
	private Integer noVolunOnceQuarter;
	private Integer percentVolunOnceQuarter;
	private Integer noVolunOnceMonth;
	private Integer percentVolunOnceMonth;
	private Integer avgAsscVolunteer;
	private Integer sinceLastVolunteer;
}
