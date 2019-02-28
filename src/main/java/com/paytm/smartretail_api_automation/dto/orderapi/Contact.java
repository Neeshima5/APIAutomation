package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.ArrayList;
import java.util.List;

public class Contact {

	private List<Address> address;
	private List<Email> email;
	private boolean is_active = false;
	private boolean is_default = false;
	private List<Phone> phone;
	private List<String> urls = new ArrayList<>();

	public Contact(List<Address> address, List<Email> email, List<Phone> phone) {
		super();
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public List<Email> getEmail() {
		return email;
	}

	public void setEmail(List<Email> email) {
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

	public List<Phone> getPhone() {
		return phone;
	}

	public void setPhone(List<Phone> phone) {
		this.phone = phone;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

}
