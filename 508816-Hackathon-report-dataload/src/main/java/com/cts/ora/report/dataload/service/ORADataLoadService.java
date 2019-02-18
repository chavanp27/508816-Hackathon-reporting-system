package com.cts.ora.report.dataload.service;

import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.dataload.vo.ORADataLoadRequest;

public interface ORADataLoadService {
	
	ORAResponse loadAssociateData(ORADataLoadRequest request);

}
