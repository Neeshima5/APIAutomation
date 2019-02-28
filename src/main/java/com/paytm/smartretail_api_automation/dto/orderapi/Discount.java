package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.List;

public class Discount {

	private String code;
	private String description;
	private String display_text;
	private List<DiscountItem> items;
	private int metric;
	private String rule_uuid;
	private int status;
	private int type;
	private int value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplay_text() {
		return display_text;
	}

	public void setDisplay_text(String display_text) {
		this.display_text = display_text;
	}

	public List<DiscountItem> getItems() {
		return items;
	}

	public void setItems(List<DiscountItem> items) {
		this.items = items;
	}

	public int getMetric() {
		return metric;
	}

	public void setMetric(int metric) {
		this.metric = metric;
	}

	public String getRule_uuid() {
		return rule_uuid;
	}

	public void setRule_uuid(String rule_uuid) {
		this.rule_uuid = rule_uuid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
