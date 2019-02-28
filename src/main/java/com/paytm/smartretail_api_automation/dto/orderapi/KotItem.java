package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.List;

public class KotItem {

	private int delete_reason;
	private List<ItemSchedule> item_schedules;
	private int line_item_id;
	private int main_status;
	private int qty;
	private String reason;
	private int sub_status;

	public int getDelete_reason() {
		return delete_reason;
	}

	public void setDelete_reason(int delete_reason) {
		this.delete_reason = delete_reason;
	}

	public List<ItemSchedule> getItem_schedules() {
		return item_schedules;
	}

	public void setItem_schedules(List<ItemSchedule> item_schedules) {
		this.item_schedules = item_schedules;
	}

	public int getLine_item_id() {
		return line_item_id;
	}

	public void setLine_item_id(int line_item_id) {
		this.line_item_id = line_item_id;
	}

	public int getMain_status() {
		return main_status;
	}

	public void setMain_status(int main_status) {
		this.main_status = main_status;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getSub_status() {
		return sub_status;
	}

	public void setSub_status(int sub_status) {
		this.sub_status = sub_status;
	}

}
