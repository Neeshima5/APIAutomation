package com.paytm.smartretail_api_automation.dto.orderapi;

public class Phone {

	private String ext = "";
	private boolean is_active = true;
	private boolean is_default = true;
	private String number = "+919898989898";
	private int type = 0;
	private String uuid = "954f1b7e-4c2a-4bd0-8123-1632c2ca57cf";

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
