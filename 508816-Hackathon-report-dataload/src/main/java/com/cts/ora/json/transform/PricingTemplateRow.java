package com.cts.ora.json.transform;

import java.util.List;

public class PricingTemplateRow {
	
	private String region;
	private String countryId;
	private String entity;
	private Integer periodFrom;
	private Integer periodTo;
	private List<String> supportingDocuments;
	private List<PricingTemplateOffering> pricingData;
	private short sortId;
	private List<PricingData> pricingData2;
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public Integer getPeriodFrom() {
		return periodFrom;
	}
	public void setPeriodFrom(Integer periodFrom) {
		this.periodFrom = periodFrom;
	}
	public Integer getPeriodTo() {
		return periodTo;
	}
	public void setPeriodTo(Integer periodTo) {
		this.periodTo = periodTo;
	}
	public List<String> getSupportingDocuments() {
		return supportingDocuments;
	}
	public void setSupportingDocuments(List<String> supportingDocuments) {
		this.supportingDocuments = supportingDocuments;
	}
	public List<PricingTemplateOffering> getPricingData() {
		return pricingData;
	}
	public void setPricingData(List<PricingTemplateOffering> pricingData) {
		this.pricingData = pricingData;
	}
	public short getSortId() {
		return sortId;
	}
	public void setSortId(short sortId) {
		this.sortId = sortId;
	}
	public List<PricingData> getPricingData2() {
		return pricingData2;
	}
	public void setPricingData2(List<PricingData> pricingData2) {
		this.pricingData2 = pricingData2;
	}

}
