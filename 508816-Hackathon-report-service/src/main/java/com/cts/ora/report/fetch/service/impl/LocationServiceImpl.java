package com.cts.ora.report.fetch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.ora.report.domain.model.Location;
import com.cts.ora.report.fetch.repository.LocationRepository;
import com.cts.ora.report.fetch.service.LocationService;
import com.cts.ora.report.fetch.vo.FetchRequest;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	LocationRepository locationRepository;
	
	@Override
	public List<Integer> getLocationIds(FetchRequest request) {
		if(!request.getGeography().getPin().contains(-1)) {
			return locationRepository.getLocationsForPin(request.getGeography().getPin());
		}else if(!request.getGeography().getArea().contains(-1)) {
			return locationRepository.getLocationIdsForArea(request.getGeography().getArea());
		}else if(!request.getGeography().getCity().contains(-1)) {
			return locationRepository.getLocationIdsForCity(request.getGeography().getCity());
		}else if( !request.getGeography().getStates().contains(-1)) {
			return locationRepository.getLocationIdsForState(request.getGeography().getStates());
		}else if(!request.getGeography().getCountries().contains(-1)) {
			return locationRepository.getLocationIdsForCountry(request.getGeography().getCountries());
		}else {
			return locationRepository.getLocationId(request.getGeography().getCountries(), request.getGeography().getStates(), request.getGeography().getCity(), request.getGeography().getArea(),request.getGeography().getPin());
		}
	}

	@Override
	public Location getLocationById(Integer id) {
		return locationRepository.findById(id).get();
	}

}
