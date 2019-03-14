package com.cts.ora.report.fetch.service;

import java.util.List;

import com.cts.ora.report.domain.model.Location;
import com.cts.ora.report.fetch.vo.FetchRequest;

public interface LocationService {

	public List<Integer> getLocationIds(FetchRequest request);
	
	public Location getLocationById(Integer id);
}
