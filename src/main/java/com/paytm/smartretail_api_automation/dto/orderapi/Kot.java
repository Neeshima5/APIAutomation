package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.List;

public class Kot {

	private long createdAt;
	private String emp_uuid;
	private List<KotItem> items;
	private long kot_device_id;
	private int kot_id;
	private String kot_prefix;
	private String logs;
	private int main_status;
	private int print_count;
	private int sub_status;

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmp_uuid() {
		return emp_uuid;
	}

	public void setEmp_uuid(String emp_uuid) {
		this.emp_uuid = emp_uuid;
	}

	public List<KotItem> getItems() {
		return items;
	}

	public void setItems(List<KotItem> items) {
		this.items = items;
	}

	public long getKot_device_id() {
		return kot_device_id;
	}

	public void setKot_device_id(long kot_device_id) {
		this.kot_device_id = kot_device_id;
	}

	public int getKot_id() {
		return kot_id;
	}

	public void setKot_id(int kot_id) {
		this.kot_id = kot_id;
	}

	public String getKot_prefix() {
		return kot_prefix;
	}

	public void setKot_prefix(String kot_prefix) {
		this.kot_prefix = kot_prefix;
	}

	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

	public int getMain_status() {
		return main_status;
	}

	public void setMain_status(int main_status) {
		this.main_status = main_status;
	}

	public int getPrint_count() {
		return print_count;
	}

	public void setPrint_count(int print_count) {
		this.print_count = print_count;
	}

	public int getSub_status() {
		return sub_status;
	}

	public void setSub_status(int sub_status) {
		this.sub_status = sub_status;
	}

}
