package com.paytm.smartretail_api_automation.dto.orderapi;

public class Email {

	private String email = "apitest@gmail.com";
	private boolean is_active = true;
	private boolean is_default = true;
	private int type = 0;
	private String uuid = "a1d0444a-8a08-4e30-93d7-965e4436b72d";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public boolean isIs_default() {
		return is_default;
	}

	public void setIs_default(boolean is_default) {
		this.is_default = is_default;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
