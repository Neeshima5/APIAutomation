package com.paytm.smartretail_api_automation.dto.storecreation;

public class AddressDto {

	private String city = "";
	private String state = "";
	private String street = "";
	private String addr2 = "";
	private String zip = "";
	private String addr1 = "";
	private String country = "";
	private String near = "";

	public AddressDto() {

	}

	public AddressDto(String city, String state, String street, String addr2, String zip, String addr1, String country,
			String near) {
		super();
		this.city = city;
		this.state = state;
		this.street = street;
		this.addr2 = addr2;
		this.zip = zip;
		this.addr1 = addr1;
		this.country = country;
		this.near = near;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getStreet() {
		return street;
	}

	public String getAddr2() {
		return addr2;
	}

	public String getZip() {
		return zip;
	}

	public String getAddr1() {
		return addr1;
	}

	public String getCountry() {
		return country;
	}

	public String getNear() {
		return near;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setNear(String near) {
		this.near = near;
	}

}
