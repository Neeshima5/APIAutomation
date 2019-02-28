package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.List;

public class ChargeSplit {

	private boolean is_exclusive;
	private List<TaxSplit> taxsplit;
	private int type;
	private int value;

	public ChargeSplit(boolean is_exclusive, List<TaxSplit> taxsplit, int type, int value) {
		super();
		this.is_exclusive = is_exclusive;
		this.taxsplit = taxsplit;
		this.type = type;
		this.value = value;
	}

	public boolean isIs_exclusive() {
		return is_exclusive;
	}

	public void setIs_exclusive(boolean is_exclusive) {
		this.is_exclusive = is_exclusive;
	}

	public List<TaxSplit> getTaxsplit() {
		return taxsplit;
	}

	public void setTaxsplit(List<TaxSplit> taxsplit) {
		this.taxsplit = taxsplit;
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
