package com.paytm.smartretail_api_automation.dto.storecreation;

import java.util.List;

public class OwnerDto {

	private String phone_number = "9023578302";
	private LoginCredentials login_credentials;
	private AddressDto alternate_address;
	private List<StoreDto> stores;
	private String name;
	private AddressDto registered_address;
	private String email;
	private String gst;

	public OwnerDto() {

	}

	public OwnerDto(String phone_number, LoginCredentials login_credentials, AddressDto alternate_address,
			List<StoreDto> stores, String name, AddressDto registered_address, String email, String gst) {
		super();
		this.phone_number = phone_number;
		this.login_credentials = login_credentials;
		this.alternate_address = alternate_address;
		this.stores = stores;
		this.name = name;
		this.registered_address = registered_address;
		this.email = email;
		this.gst = gst;
	}

	public String getphone_number() {
		return phone_number;
	}

	public LoginCredentials getlogin_credentials() {
		return login_credentials;
	}

	public AddressDto getalternate_address() {
		return alternate_address;
	}

	public List<StoreDto> getStores() {
		return stores;
	}

	public String getName() {
		return name;
	}

	public AddressDto getRegistered_address() {
		return registered_address;
	}

	public String getEmail() {
		return email;
	}

	public String getGst() {
		return gst;
	}

	public void setphone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public void setlogin_credentials(LoginCredentials login_credentials) {
		this.login_credentials = login_credentials;
	}

	public void setalternate_address(AddressDto alternate_address) {
		this.alternate_address = alternate_address;
	}

	public void setStores(List<StoreDto> stores) {
		this.stores = stores;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegistered_address(AddressDto registered_address) {
		this.registered_address = registered_address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

}
