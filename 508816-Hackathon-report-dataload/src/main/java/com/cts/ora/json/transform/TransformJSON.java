package com.cts.ora.json.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.apple.ist.rins2.commons.vo.BaseOption;
import com.apple.ist.rins2.uiservice.filter.vo.ContentType;
import com.apple.ist.rins2.uiservice.refdata.pricing.bulkupdate.vo.PricingBulkUpdateConstants;
import com.apple.ist.rins2.uiservice.refdata.pricing.bulkupdate.vo.PricingData;
import com.apple.ist.rins2.uiservice.refdata.pricing.bulkupdate.vo.PricingTemplateOffering;
import com.apple.ist.rins2.uiservice.refdata.pricing.bulkupdate.vo.PricingTemplateOfferingRate;

public class TransformJSON {
	
	
	private List<PricingTemplateOffering> transform(List<PricingData> pricingTemplateData) {
		List<PricingTemplateOffering> pricingTemplateOffering = new ArrayList<>();
		PricingTemplateOffering template = null;
		List<PricingTemplateOfferingRate> pricingOfferings = null;
		
		List<BaseOption> serviceTypes = filterDao.fetchStoreTypes();
		List<ContentType> contentTypes = filterDao.fetchContentTypes();
		Map<String,List<String>> serviceTypeOfferingMap = new HashMap<>();
		
		serviceTypes.stream().forEach(s->{
			List<String> cntTypes = contentTypes.stream().filter(c->c.getServiceTypeId().equals(s.getId()))
												.map(c->c.getContentGroupName()).collect(Collectors.toList());
			serviceTypeOfferingMap.merge(s.getValue(), cntTypes, (l1,l2)->
						Stream.of(l1,l2).flatMap(Collection::stream).collect(Collectors.toList()));
		});
		logger.append(PRIC_BULKUPDATE_SERVICE, "serviceTypeOfferingMap=="+serviceTypeOfferingMap).info();
		
		Map<String,PricingTemplateOffering> servTypeTemplateMap = new HashMap<>();
		for(PricingData data:pricingTemplateData) {
			String name = data.getName();
			String attr = data.getAttribute();
			boolean isServiceType = serviceTypes.stream().filter(s->s.getValue().equals(name)).count()>0;
			boolean isContentType = contentTypes.stream().filter(c->c.getContentGroupName().equals(name)).count()>0;
			
			if(isServiceType) {
				if(servTypeTemplateMap.containsKey(name)) {
					template = servTypeTemplateMap.get(name);
				}else {
					template = new PricingTemplateOffering();
					template.setServiceType(name);
					servTypeTemplateMap.put(name, template);
					
					if(attr==null) {
						continue;
					}else if(attr.equals(PricingBulkUpdateConstants.JSON_ATTR_CURRENCY)){
						template.setCurrency(data.getValue());
					}else if(attr.equals(PricingBulkUpdateConstants.JSON_ATTR_RATE)){
						template.setRate(Double.parseDouble(data.getValue()));
					}else if(attr.equals(PricingBulkUpdateConstants.JSON_ATTR_MUSIC_VIDEO_2X)){
						template.setMusicVideo2x(data.getValue());
					}
					template.setPricingOfferings(new ArrayList<>());
				}
			}else if(isContentType) {
				
			}
			
			if(servTypeTemplateMap.containsKey(name)) {
				template = servTypeTemplateMap.get(name);
			}else {
				template = new PricingTemplateOffering();
				template.setServiceType(name);
				servTypeTemplateMap.put(name, template);
				
				if(attr==null) {
					continue;
				}else if(attr.equals(PricingBulkUpdateConstants.JSON_ATTR_CURRENCY)){
					template.setCurrency(data.getValue());
				}else if(attr.equals(PricingBulkUpdateConstants.JSON_ATTR_RATE)){
					template.setRate(Double.parseDouble(data.getValue()));
				}else if(attr.equals(PricingBulkUpdateConstants.JSON_ATTR_MUSIC_VIDEO_2X)){
					template.setMusicVideo2x(data.getValue());
				}
				template.setPricingOfferings(new ArrayList<>());
			}
			
			pricingOfferings = template.getPricingOfferings();
			
			
			
		}
		
		logger.append(PRIC_BULKUPDATE_SERVICE, "Final pricingTemplateOffering=="+pricingTemplateOffering).info();
		return pricingTemplateOffering;
	}

}
