package com.paytm.smartretail_api_automation.dto.orderapi;

public class DiscountItem {

	private int consumed_qty;
	private int line_item_id;
	private int metric;
	private int value;

	public int getConsumed_qty() {
		return consumed_qty;
	}

	public void setConsumed_qty(int consumed_qty) {
		this.consumed_qty = consumed_qty;
	}

	public int getLine_item_id() {
		return line_item_id;
	}

	public void setLine_item_id(int line_item_id) {
		this.line_item_id = line_item_id;
	}

	public int getMetric() {
		return metric;
	}

	public void setMetric(int metric) {
		this.metric = metric;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
