package com.paytm.smartretail_api_automation.dto.orderapi;

public class Table {

	private int guest_count;
	private boolean is_active;
	private String merged_name;
	private String name;
	private int status;
	private int id;

	public int getGuest_count() {
		return guest_count;
	}

	public void setGuest_count(int guest_count) {
		this.guest_count = guest_count;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public String getMerged_name() {
		return merged_name;
	}

	public void setMerged_name(String merged_name) {
		this.merged_name = merged_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
