package com.cts.ora.json.transform;

import java.util.List;

import org.springframework.data.annotation.Id;

public class PricingTemplate {
	
	@Id
	private String id;
	private Long templateId;
	private String type;
	private List<PricingTemplateRow> pricingTemplate;
	

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<PricingTemplateRow> getPricingTemplate() {
		return pricingTemplate;
	}
	public void setPricingTemplate(List<PricingTemplateRow> pricingTemplate) {
		this.pricingTemplate = pricingTemplate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

}
