package com.cts.ora.json.transform;

import java.math.BigDecimal;

public class PricingTemplateOfferingRate {

	private String offering;
	private Long contentGrpId;
	private BigDecimal psm;
	private BigDecimal ppr;
	private BigDecimal lhMinima;
	private boolean delete;
	
	public String getOffering() {
		return offering;
	}
	public void setOffering(String offering) {
		this.offering = offering;
	}
	public BigDecimal getPsm() {
		return psm;
	}
	public void setPsm(BigDecimal psm) {
		this.psm = psm;
	}
	public BigDecimal getPpr() {
		return ppr;
	}
	public void setPpr(BigDecimal ppr) {
		this.ppr = ppr;
	}
	public BigDecimal getLhMinima() {
		return lhMinima;
	}
	public void setLhMinima(BigDecimal lhMinima) {
		this.lhMinima = lhMinima;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public Long getContentGrpId() {
		return contentGrpId;
	}
	public void setContentGrpId(Long contentGrpId) {
		this.contentGrpId = contentGrpId;
	}
	
}
