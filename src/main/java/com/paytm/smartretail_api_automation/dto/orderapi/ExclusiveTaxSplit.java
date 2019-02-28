package com.paytm.smartretail_api_automation.dto.orderapi;

public class ExclusiveTaxSplit {

	private boolean is_exclusive;
	private int type;
	private int value;

	public boolean isIs_exclusive() {
		return is_exclusive;
	}

	public void setIs_exclusive(boolean is_exclusive) {
		this.is_exclusive = is_exclusive;
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
