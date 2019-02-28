package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.List;

public class ChargeDetails {

	private List<Details> details;
	private String name;
	private String uuid;

	public List<Details> getDetails() {
		return details;
	}

	public void setDetails(List<Details> details) {
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
