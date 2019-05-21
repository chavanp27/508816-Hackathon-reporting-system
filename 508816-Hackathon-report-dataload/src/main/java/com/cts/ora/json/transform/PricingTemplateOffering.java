package com.cts.ora.json.transform;

import java.util.List;

public class PricingTemplateOffering {
	
	private String serviceType;
	private Long serviceTypeId;
	private String currency;
	private Double rate;
	private String musicVideo2x;
	private List<PricingTemplateOfferingRate> pricingOfferings;
	
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public List<PricingTemplateOfferingRate> getPricingOfferings() {
		return pricingOfferings;
	}
	public void setPricingOfferings(List<PricingTemplateOfferingRate> pricingOfferings) {
		this.pricingOfferings = pricingOfferings;
	}
	public String getMusicVideo2x() {
		return musicVideo2x;
	}
	public void setMusicVideo2x(String musicVideo2x) {
		this.musicVideo2x = musicVideo2x;
	}
	public Long getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	

}
