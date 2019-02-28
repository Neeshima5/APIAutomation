package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.List;

public class TaxDetail {

	private List<String> children;
	private boolean inclusive;
	private int metric;
	private String name;
	private int scaling_factor;
	private int type;
	private int value;

	public List<String> getChildren() {
		return children;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}

	public boolean isInclusive() {
		return inclusive;
	}

	public void setInclusive(boolean inclusive) {
		this.inclusive = inclusive;
	}

	public int getMetric() {
		return metric;
	}

	public void setMetric(int metric) {
		this.metric = metric;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScaling_factor() {
		return scaling_factor;
	}

	public void setScaling_factor(int scaling_factor) {
		this.scaling_factor = scaling_factor;
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
