package com.paytm.smartretail_api_automation.dto.storecreation;

public class LoginCredentials {

	String web_password = "APIAutomation*123#";
	String device_password = "1234";
	
	public LoginCredentials() {
		
	}

	public LoginCredentials(String web_password, String device_password) {
		super();
		this.web_password = web_password;
		this.device_password = device_password;
	}

	public String getweb_password() {
		return web_password;
	}

	public String getdevice_password() {
		return device_password;
	}

	public void setweb_password(String web_password) {
		this.web_password = web_password;
	}

	public void setdevice_password(String device_password) {
		this.device_password = device_password;
	}

}
