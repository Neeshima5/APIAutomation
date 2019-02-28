package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.List;

public class TaxDetails {

	private List<TaxDetail> details;
	private String name;
	private String uuid;

	public TaxDetails(List<TaxDetail> details, String name, String uuid) {
		super();
		this.details = details;
		this.name = name;
		this.uuid = uuid;
	}

	public List<TaxDetail> getDetails() {
		return details;
	}

	public void setDetails(List<TaxDetail> details) {
		this.details = details;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
