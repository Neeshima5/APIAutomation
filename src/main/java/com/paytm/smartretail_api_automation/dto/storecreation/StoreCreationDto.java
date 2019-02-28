package com.paytm.smartretail_api_automation.dto.storecreation;

public class StoreCreationDto {

	private String phone_number;
	private LoginCredentials login_credentials;
	private StoreDto store;
	private String name;
	private AddressDto registered_address;
	private String email;
	private String gst;

	public StoreCreationDto() {

	}

	public StoreCreationDto(String phone_number, LoginCredentials login_credentials, StoreDto store, String name,
			AddressDto registered_address, String email, String gst) {
		super();
		this.phone_number = phone_number;
		this.login_credentials = login_credentials;
		this.store = store;
		this.name = name;
		this.registered_address = registered_address;
		this.email = email;
		this.gst = gst;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public LoginCredentials getLogin_credentials() {
		return login_credentials;
	}

	public void setLogin_credentials(LoginCredentials login_credentials) {
		this.login_credentials = login_credentials;
	}

	public StoreDto getStore() {
		return store;
	}

	public void setStore(StoreDto store) {
		this.store = store;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AddressDto getRegistered_address() {
		return registered_address;
	}

	public void setRegistered_address(AddressDto registered_address) {
		this.registered_address = registered_address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

}