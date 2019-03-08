package com.cts.ora.report.fetch.service;

import java.util.List;

import com.cts.ora.report.fetch.vo.FetchRequest;

public interface LocationService {

	public List<Integer> getLocationIds(FetchRequest request);
}
