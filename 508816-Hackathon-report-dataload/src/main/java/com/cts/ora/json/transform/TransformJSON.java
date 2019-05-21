package com.cts.ora.json.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformJSON {
	
	
	private List<PricingTemplateOffering> transform(List<PricingData> pricingTemplateData) {
		List<PricingTemplateOffering> pricingTemplateOffering = new ArrayList<>();
		PricingTemplateOffering template = null;
		List<PricingTemplateOfferingRate> pricingOfferings = null;
		
		Map<String,List<String>> serviceTypeOfferingMap = new HashMap<>();
		
		
		contentTypes.stream().forEach(c->{
			String key = serviceTypes.stream().filter(s->s.getId().equals(c.getServiceTypeId())).map(s->s.getValue()).toString();
			serviceTypeOfferingMap.computeIfAbsent(key, c->{
				
			});
			
		});

}
